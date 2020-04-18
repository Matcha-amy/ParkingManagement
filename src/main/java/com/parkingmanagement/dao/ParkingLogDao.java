package com.parkingmanagement.dao;

import com.parkingmanagement.entity.ParkingLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ParkingLogDao {

    List<ParkingLog> getList(HashMap<String, Object> query);

    void save(ParkingLog parkingLog);

    void updateParking(ParkingLog parkingLog);
}
