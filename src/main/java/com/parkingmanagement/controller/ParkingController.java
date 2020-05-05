package com.parkingmanagement.controller;


import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.service.ParkingService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;


    @RequiresRoles({"admin"})
    @RequestMapping(value = "/toParking")
    public String toParking(){
        return "/base/admin/park.html";
    }

    //查看
    @ResponseBody
    @RequestMapping(value = "/list")
    public List<Parking>  getList(){
        List<Parking> list = new ArrayList<>();
        try{
            list = parkingService.getList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return  list;
    }

    //增加
    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping(value = "/addParking",method = RequestMethod.POST)
    public BaseResult addParking(Parking parking){
        BaseResult result = new BaseResult();
        try{
            result = parkingService.addParking(parking);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("增加停车场失败，请重新添加");
        }
        return result;
    }

    //修改
    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping(value = "/updateParking",method = RequestMethod.POST)
    public BaseResult updateParking(Parking parking) {
        BaseResult result = new BaseResult();
        try {
            result = parkingService.updateParking(parking);
        } catch (Exception e) {
            e.printStackTrace();
            return result.setMsg("修改停车场失败，请重新修改");
        }
        return result;
    }

    //删除
    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping(value = "/delParking",method = RequestMethod.POST)
    public BaseResult delParking(Parking parking) {
        BaseResult result = new BaseResult();
        try {
            parking.setParkingStatus(1);
            result = parkingService.updateParking(parking);
        } catch (Exception e) {
            e.printStackTrace();
            return result.setMsg("删除停车场失败，请重新选择删除");
        }
        return result.setMsg("删除停车场信息成功");
    }
}
