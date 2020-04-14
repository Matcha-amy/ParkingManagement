package com.parkingmanagement.dao;

import com.parkingmanagement.entity.ParkingLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ParkingLogDao {

    List<ParkingLog> query(HashMap<String, Object> query);
}
