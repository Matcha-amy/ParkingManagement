package com.parkingmanagement.controller;


import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.entity.vo.ParkingLogVO;
import com.parkingmanagement.service.ParkingLogService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/base/parkingLog")
public class ParkingLogController {

    @Autowired
    private ParkingLogService parkingLogService;


    @RequestMapping(value = "/toParkingLog")
    public String toParking(){
        return "/base/admin/parkingLog.html";
    }


    @ResponseBody
    @RequestMapping("/list")
    public List<ParkingLogVO> getList(){
        List<ParkingLogVO> parkingLogList = new ArrayList<>();
        try {
            parkingLogList = parkingLogService.getList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return parkingLogList;
    }


    @ResponseBody
    @RequestMapping("/add")
    public BaseResult getList(ParkingLogVO parkingLogVO){
        BaseResult result = new BaseResult();
        try {
            result = parkingLogService.addParkingLog(parkingLogVO);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("添加停车记录失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/update")
    public BaseResult updateParkingLog(ParkingLogVO parkingLogVO){
        BaseResult result = new BaseResult();
        try {
            result = parkingLogService.updateParkingLog(parkingLogVO);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("修改车记录失败");
        }
        return result;
    }

}
