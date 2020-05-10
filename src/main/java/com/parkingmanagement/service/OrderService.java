package com.parkingmanagement.service;

import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.utils.BaseResult;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    List<OrderVO> getList();

    BaseResult addOrder(OrderVO orderVO) throws ParseException;

    BaseResult updateOrder(OrderVO orderVO) throws ParseException;

    List<OrderVO> getUserList(String userName);
}
