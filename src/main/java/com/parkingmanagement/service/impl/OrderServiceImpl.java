package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.OrderCarportDao;
import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderCarportDao orderCarportDao;


    @Override
    public List<OrderVO> getList() {
       List<OrderVO> orderVOList =orderCarportDao.getVOList(null);
        for (OrderVO orderVO : orderVOList) {
            String time = TimeUtils.timeToStr(new Long(orderVO.getOrderCarportTime()));
            orderVO.setOrderCarportTime(time);
        }
        return orderVOList;
    }



}
