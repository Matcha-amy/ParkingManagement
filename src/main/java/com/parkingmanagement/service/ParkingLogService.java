package com.parkingmanagement.service;

import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.entity.vo.ParkingLogVO;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface ParkingLogService {

    List<ParkingLogVO> getList();

    List<ParkingLogVO> getList(String userName);

    BaseResult addParkingLog(ParkingLogVO parkingLogVO);

    BaseResult updateParkingLog(ParkingLogVO parkingLogVO);
}
