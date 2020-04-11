package com.parkingmanagement.service;

import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface PlateService {
    List<Plate> getList(String userName);

    BaseResult updatePlate(Plate plate);


    BaseResult addPlate(Plate plate);
}
