package com.parkingmanagement.dao;

import com.parkingmanagement.entity.PaymentLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentDao {

    List<PaymentLog> getList(@Param("paymentUserId") Integer paymentUserId);
}
