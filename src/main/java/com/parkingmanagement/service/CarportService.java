package com.parkingmanagement.service;

import com.parkingmanagement.entity.Carport;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface CarportService {
    List<Carport> getList(ListQuery query);

    BaseResult addCarport(Carport carport);

    BaseResult updateCarport(Carport carport);

    BaseResult delCarport(Carport carport);

    List<Carport> getCarportByParking(String parkingName);
}
