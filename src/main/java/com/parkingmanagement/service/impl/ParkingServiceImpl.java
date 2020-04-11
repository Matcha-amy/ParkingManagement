package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.ParkingDao;
import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.service.ParkingService;
import com.parkingmanagement.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingDao parkingDao;


    @Override
    public List<Parking> getList() {
        return parkingDao.getList();
    }

    @Override
    public BaseResult addParking(Parking parking) {
        BaseResult result = new BaseResult();
        if (parking == null || parking.getParkingName() == null || parking.getParkingCarportSize() == null) {
            return result.setMsg("添加的停车场信息为空，请重新填写后添加");
        }
        Parking dbParking = parkingDao.getByName(parking.getParkingName());
        if (dbParking != null) {
            return result.setMsg("该停车场信息已添加，不能重复添加");
        }
        parkingDao.save(parking);
        return result.setStatus(true).setMsg("添加停车场信息成功");
    }

    @Override
    public BaseResult updateParking(Parking parking) {
        BaseResult result = new BaseResult();
        if (parking == null || parking.getParkingName() == null || parking.getParkingCarportSize() == null || parking.getParkingId() == null) {
            return result.setMsg("停车场信息为空，请重新选择");
        }
        Parking dbParking = parkingDao.getById(parking.getParkingId());
        if (dbParking == null) {
            return result.setMsg("停车场不存在，请重新选择");
        }
        if (dbParking.equals(parking)) {
            return result.setMsg("无修改，请修改后提交");
        }
        parkingDao.updateParking(parking);
        return result.setStatus(true).setMsg("修改停车场信息成功");
    }
}
