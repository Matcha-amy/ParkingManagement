package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.CarportDao;
import com.parkingmanagement.dao.OrderCarportDao;
import com.parkingmanagement.dao.ParkingDao;
import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.Carport;
import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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


    public OrderCarport VOToOrderCarport(OrderVO orderVO) throws ParseException {
        OrderCarport order = new OrderCarport();
        order.setOrderCarportStatus(Integer.parseInt(orderVO.getOrderCarportStatus()));
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


}
