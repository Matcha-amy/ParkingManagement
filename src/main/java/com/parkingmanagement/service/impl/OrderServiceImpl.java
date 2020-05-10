package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.*;
import com.parkingmanagement.entity.*;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.Constant;
import com.parkingmanagement.utils.TimeUtils;
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
        BaseResult baseResult = new BaseResult();
        OrderCarport orderCarport = VOToOrderCarport(orderVO);
        orderCarportDao.save(orderCarport);
        return baseResult.setStatus(true).setMsg("添加成功");
    }

    @Override
    public BaseResult updateOrder(OrderVO orderVO) throws ParseException {
        BaseResult result =  new BaseResult();
        //完成或取消
        //更改预约表 将预约状态改为完成
        OrderCarport orderCarport = VOToOrderCarport(orderVO);
        orderCarportDao.update(orderCarport);
        if(orderCarport.getOrderCarportStatus() == Constant.ORDER_STATUS_SUCCESS){
            //预约完成
            //更改停车记录表  生成停车记录
            ParkingLog parkingLog = initParkingLog(orderCarport);
            parkingLogDao.save(parkingLog);
            //同时更改车位表的该车位状态
            Carport carport = carportDao.getById(orderCarport.getOrderCarportCarportId());
            carport.setCarportStatus(Constant.CARPORT_STATUS_ORDER);
            carportDao.updateCarport(carport);
        }
        return result.setStatus(true);
    }


    public OrderCarport VOToOrderCarport(OrderVO orderVO) throws ParseException {
        OrderCarport order = new OrderCarport();
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
        return order;
    }

    private ParkingLog initParkingLog(OrderCarport orderCarport){
        ParkingLog parkingLog = new ParkingLog();
        parkingLog.setParkingLogUserId(orderCarport.getOrderCarportUserId());
        parkingLog.setParkingLogPlateId(orderCarport.getOrderPlateId());
        Carport carport = carportDao.getById(orderCarport.getOrderCarportCarportId());
        parkingLog.setParkingLogParkingId(carport.getParkingId());
        parkingLog.setParkingLogStartTime(new Date().getTime());
        return parkingLog;
    }


}
