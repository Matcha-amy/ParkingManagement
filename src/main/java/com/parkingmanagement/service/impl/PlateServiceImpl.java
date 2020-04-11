package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.PlateDao;
import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.PlateService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PlateDao plateDao;

    @Override
    public List<Plate> getList(String userName) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",userName);
        List<User> users = userDao.query(queryMap);
        if (users.isEmpty()){
            return null;
        }
        List<Plate> plateList =  plateDao.getList(users.get(0).getUserId());
        return plateList;
    }

    @Override
    public BaseResult updatePlate(Plate plate) {
        BaseResult result = new BaseResult();
        if (plate==null ||plate.getPlateId()==null){
            return result.setMsg("没有选中车牌或该车牌无效");
        }
        Plate dbPlate =  plateDao.getById(plate.getPlateId());
        if (dbPlate ==null){
            return result.setMsg("没有选中车牌或该车牌无效");
        }
        if (dbPlate.equals(plate)){
            return result.setMsg("该车牌无修改，请修改后再更改");
        }
        plateDao.updatePlate(plate);
        return result.setStatus(true).setMsg("更改车牌成功");
    }

    @Override
    public BaseResult addPlate(Plate plate) {
        BaseResult result = new BaseResult();
        if (plate==null ||plate.getPlateId()==null){
            return result.setMsg("没有选中车牌或该车牌无效");
        }
        String userName=(String) SecurityUtils.getSubject().getPrincipal();
        User user = userDao.getUserByUsername(userName);
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("plateCode",plate.getPlateCode());
        queryMap.put("userId",user.getUserId());
        Plate dbPlate =  plateDao.query(queryMap);
        if (dbPlate !=null){
            return result.setMsg("已添加该车牌，不能重复添加");
        }
        plate.setUserId(user.getUserId());
        plateDao.save(plate);
        return result.setStatus(true).setMsg("添加车牌成功");
    }
}
