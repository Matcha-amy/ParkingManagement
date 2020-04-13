package com.parkingmanagement.service;

import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface ParkingLogService {

    List<ParkingLog> getList(ListQuery query);

    BaseResult addParkingLog(ParkingLog parkingLog);
}
