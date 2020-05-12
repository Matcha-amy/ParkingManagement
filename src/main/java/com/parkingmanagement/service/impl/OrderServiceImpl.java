package com.parkingmanagement.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.dao.*;
import com.parkingmanagement.entity.*;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataUnit;
import org.thymeleaf.util.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderCarportDao orderCarportDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ParkingDao parkingDao;
    @Autowired
    private CarportDao carportDao;
    @Autowired
    private ParkingLogDao parkingLogDao;
    @Autowired
    private PlateDao plateDao;
    @Autowired
    private DelayQueueManager delayQueueManager;




    @Override
    public List<OrderVO> getList() {
       List<OrderVO> orderVOList =orderCarportDao.getVOList(null);
        for (OrderVO orderVO : orderVOList) {
            String time = TimeUtils.timeToStr(new Long(orderVO.getOrderCarportTime()));
            orderVO.setOrderCarportTime(time);
        }
        return orderVOList;
    }

    @Override
    public BaseResult addOrder(OrderVO orderVO) throws ParseException {
        BaseResult result = new BaseResult();
        OrderCarport orderCarport = VOToOrderCarport(orderVO);
        //先要判断 用户是否已在预约车位中
        OrderCarport dbOrderCarport = orderCarportDao.selectOrdering(orderCarport.getOrderCarportUserId());
        if (dbOrderCarport != null) {
            return result.setMsg("已有预约车位，无法再次预约");
        }
        //在判断 用户这个月内的违约次数是否超过三次
        Integer breakCount = orderCarportDao.breakOrder(orderCarport.getOrderCarportUserId());
        if (breakCount >= Constant.BRESK_ORDER_MAX) {
            return result.setMsg("本月违约次数过多，无法再次预约");
        }
        orderCarportDao.save(orderCarport);
        Carport carport = new Carport();
        carport.setCarportStatus(Constant.CARPORT_STATUS_ORDER);
        carport.setCarportId(orderCarport.getOrderCarportCarportId());
        carportDao.updateCarport(carport);
        //开启延迟任务
        TaskBase taskBase = new TaskBase(JSONObject.toJSONString(orderCarport));
        DelayTask delayTask = new DelayTask(taskBase,5000);//这个时间可以自定义  是以毫秒为单位
        delayQueueManager.put(delayTask);
        return result.setStatus(true).setMsg("预约成功");
    }

    @Override
    public BaseResult updateOrder(OrderVO orderVO) throws ParseException {
        BaseResult result =  new BaseResult();
        //完成或取消
        //更改预约表 将预约状态改为完成
        OrderCarport orderCarport = VOToOrderCarport(orderVO);
        if(orderCarport.getOrderCarportStatus() == Constant.ORDER_STATUS_SUCCESS){
            orderCarport.setOrderCarportStatus(Constant.ORDER_STATUS_SUCCESS);
            orderCarportDao.update(orderCarport);
            //预约完成
            //更改停车记录表  生成停车记录
            ParkingLog parkingLog = initParkingLog(orderCarport);
            parkingLogDao.save(parkingLog);
            //同时更改车位表的该车位状态
            Carport carport = carportDao.getById(orderCarport.getOrderCarportCarportId());
            carport.setCarportStatus(Constant.CARPORT_STATUS_USEING);
            carportDao.updateCarport(carport);
        }
        return result.setStatus(true);
    }

    @Override
    public List<OrderVO> getUserList(String userName) {
        User userByUsername = userDao.getUserByUsername(userName);
        List<OrderVO> orderVOList =orderCarportDao.getVOList(userByUsername.getUserId());
        for (OrderVO orderVO : orderVOList) {
            String time = TimeUtils.timeToStr(new Long(orderVO.getOrderCarportTime()));
            orderVO.setOrderCarportTime(time);
        }
        return orderVOList;
    }


    public OrderCarport VOToOrderCarport(OrderVO orderVO) throws ParseException {
        OrderCarport order = new OrderCarport();
        order.setOrderCarportId(orderVO.getOrderCarportId());
        order.setOrderCarportStatus(orderVO.getOrderCarportStatus());
        order.setOrderCarportTime(TimeUtils.strToTime(  orderVO.getOrderCarportTime()));
        order.setOrderCarportUserId( userDao.getUserByUsername(orderVO.getUsername()).getUserId());
        String parkingName = orderVO.getParkingName();
        Parking parking = parkingDao.getByName(parkingName);
        String carportNum = orderVO.getCarportNum();
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("parkingId",parking.getParkingId());
        queryMap.put("carportNum",carportNum);
        List<Carport> carportList = carportDao.query(queryMap);
        if (carportList.isEmpty()){
            throw new RuntimeException("填写信息有误");
        }else {
            order.setOrderCarportCarportId(carportList.get(0).getCarportId());
        }
        HashMap<String,Object> plateQueryMap = new HashMap<>();
        plateQueryMap.put("plateCode",orderVO.getPlateCode());
        List<Plate> plates = plateDao.query(plateQueryMap);
        order.setOrderPlateId(plates.get(0).getPlateId());
        return order;
    }

    private ParkingLog initParkingLog(OrderCarport orderCarport){
        ParkingLog parkingLog = new ParkingLog();
        parkingLog.setParkingLogCarportId(orderCarport.getOrderCarportCarportId());
        parkingLog.setParkingLogUserId(orderCarport.getOrderCarportUserId());
        parkingLog.setParkingLogPlateId(orderCarport.getOrderPlateId());
        Carport carport = carportDao.getById(orderCarport.getOrderCarportCarportId());
        parkingLog.setParkingLogParkingId(carport.getParkingId());
        parkingLog.setParkingLogStartTime(new Date().getTime());
        return parkingLog;
    }


}
