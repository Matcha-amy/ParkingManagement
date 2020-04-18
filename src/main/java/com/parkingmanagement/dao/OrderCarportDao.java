package com.parkingmanagement.dao;

import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderCarportDao {
    OrderCarport selectOrdering( Integer userId );

    Integer breakOrder(Integer userId);

    Integer save(OrderCarport orderCarport);

    OrderCarport findByTime(Long orderCarportTime);

    void update(OrderCarport dbOrderCarport);

    List<OrderVO> getVOList(Integer userId);
}
