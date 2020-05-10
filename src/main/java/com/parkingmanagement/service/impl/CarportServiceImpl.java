package com.parkingmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.parkingmanagement.dao.CarportDao;
import com.parkingmanagement.dao.ParkingDao;
import com.parkingmanagement.entity.Carport;
import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.service.CarportService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CarportServiceImpl implements CarportService {

    @Autowired
    private CarportDao carportDao;
    @Autowired
    private ParkingDao parkingDao;

    @Override
    public List<Carport> getList(ListQuery query) {
        PageHelper.startPage(query.getLimit(), query.getPage());
        List<Carport> carportList = carportDao.query(query.getQuery());
        PageInfo<Carport> page = new PageInfo<Carport>(carportList);
        return page.getList();
    }

    @Override
    public BaseResult addCarport(Carport carport) {
        BaseResult result = new BaseResult();
        if(carport ==null  || carport.getParkingId() == null){
            return result.setMsg("没有车位信息，添加失败");
        }
        carportDao.save(carport);
        return result.setMsg("添加车位信息成功").setStatus(true);
    }

    @Override
    public BaseResult updateCarport(Carport carport) {
        BaseResult result = new BaseResult();
        if (carport  == null || carport.getCarportId() == null){
            return result.setMsg("没有选择车位，请重新选择");
        }
        Carport dbCarport = carportDao.getById(carport.getCarportId());
        if (dbCarport == null ){
            return result.setMsg("没有选择车位，请重新选择");
        }
        if (dbCarport.equals(carport)){
            return result.setMsg("该车位没有修改,请更改后修改");
        }
        carportDao.updateCarport(carport);
        return result.setStatus(true).setMsg("修改成功！");
    }

    @Override
    public BaseResult delCarport(Carport carport) {
        BaseResult result = new BaseResult();
        if (carport  == null || carport.getCarportId() == null){
            return result.setMsg("没有选择车位，请重新选择");
        }
        Carport dbCarport = carportDao.getById(carport.getCarportId());
        if (dbCarport == null ){
            return result.setMsg("没有选择车位，请重新选择");
        }
        carport.setCarportStatus(3);
        carportDao.updateCarport(carport);
        return result.setStatus(true).setMsg("禁用车位成功");
    }

    @Override
    public List<Carport> getCarportByParking(String parkingName) {
        Parking parking = parkingDao.getByName(parkingName);
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("carportStatus",Constant.CARPORT_STATUS_FREE);
        queryMap.put("parkingId",parking.getParkingId());
        List<Carport> carportList = carportDao.query(queryMap);
        return carportList;
    }


}
