package com.parkingmanagement.dao;

import com.parkingmanagement.entity.OrderCarport;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCarportDao {
    OrderCarport selectOrdering( Integer userId );

    Integer breakOrder(Integer userId);

    Integer save(OrderCarport orderCarport);

    OrderCarport findByTime(Long orderCarportTime);

    void update(OrderCarport dbOrderCarport);
}
