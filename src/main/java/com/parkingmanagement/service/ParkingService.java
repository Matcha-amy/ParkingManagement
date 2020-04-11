package com.parkingmanagement.service;

import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface ParkingService {
    List<Parking> getList();

    BaseResult addParking(Parking parking);

    BaseResult updateParking(Parking parking);
}
