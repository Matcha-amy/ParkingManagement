package com.parkingmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.parkingmanagement.dao.ParkingLogDao;
import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.service.ParkingLogService;
import com.parkingmanagement.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLogServiceImpl implements ParkingLogService {

    @Autowired
    private ParkingLogDao parkingLogDao;

    @Override
    public List<ParkingLog> getList(ListQuery query) {
        PageHelper.startPage(query.getLimit(), query.getPage());
        List<ParkingLog>  parkingLogList= parkingLogDao.query(query.getQuery());
        PageInfo<ParkingLog> page = new PageInfo<ParkingLog>(parkingLogList);
        return page.getList();
    }

    @Override
    public BaseResult addParkingLog(ParkingLog parkingLog) {

        return null;
    }
}
