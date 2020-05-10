package com.parkingmanagement.controller;

import com.parkingmanagement.entity.Carport;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.service.CarportService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/carport")
public class CarportController {

    @Autowired
    private CarportService carportService;

    @RequiresRoles({"admin"})
    @RequestMapping("/toCarport")
    public String toCarport(){
        return "/base/admin/carport.html";
    }



    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping("/list")
    public List<Carport> getList(ListQuery query){
        List<Carport> carportList = new ArrayList<>();
        try {
            carportList = carportService.getList(query);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return carportList;
    }


    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping("/addCarport")
    public BaseResult addCarport(Carport carport){
        BaseResult result  = new BaseResult();
        try {
            result = carportService.addCarport(carport);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("添加车位信息失败");
        }
        return result;
    }

    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping("/updateCarport")
    public BaseResult updateCarport(Carport carport){
        BaseResult result  = new BaseResult();
        try {
            result = carportService.updateCarport(carport);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("修改车位信息失败");
        }
        return result;
    }

    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping("/delCarport")
    public BaseResult delCarport(Carport carport){
        BaseResult result  = new BaseResult();
        try {
            result = carportService.delCarport(carport);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("禁用车位信息失败");
        }
        return result;
    }

    //获取该停车场的所有车位
    @ResponseBody
    @RequestMapping("/getCarportByParking")
    public  List<Carport> getCarportByParking(String parkingName ){
        List<Carport> carportList = new ArrayList<>();
        try {
            carportList = carportService.getCarportByParking(parkingName);
        }catch (Exception e){
            e.printStackTrace();
            return carportList ;
        }
        return carportList;
    }
}
