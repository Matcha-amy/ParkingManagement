package com.parkingmanagement.service;

import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.utils.BaseResult;

import java.text.ParseException;
import java.util.List;

public interface PaymentService {
    List<OrderVO> getList();


}
