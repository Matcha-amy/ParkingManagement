package com.parkingmanagement.dao;

import com.parkingmanagement.entity.Parking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParkingDao {

    List<Parking> getList();

    Parking getByName(String parkingName);

    void save(Parking parking);

    Parking getById(Integer parkingId);

    void updateParking(Parking parking);
}
