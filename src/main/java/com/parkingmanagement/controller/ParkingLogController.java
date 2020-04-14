package com.parkingmanagement.controller;


import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.vo.ListQuery;
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
@RequestMapping("/user/parkingLog")
public class ParkingLogController {

    @Autowired
    private ParkingLogService parkingLogService;

    @RequiresRoles({"user"})
    @RequestMapping(value = "/toParkingLog")
    public String toParking(){
        return "/base/admin/parkingLog,html";
    }

    @RequiresRoles({"user"})
    @ResponseBody
    @RequestMapping("/list")
    public List<ParkingLog> getList(ListQuery query){
        List<ParkingLog> parkingLogList = new ArrayList<>();
        try {
            parkingLogList = parkingLogService.getList(query);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return parkingLogList;
    }

    @RequiresRoles({"user"})
    @ResponseBody
    @RequestMapping("/add")
    public BaseResult getList(ParkingLog parkingLog){
        BaseResult result = new BaseResult();
        try {
            result = parkingLogService.addParkingLog(parkingLog);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("添加停车记录失败");
        }
        return result;
    }

    @RequiresRoles({"user"})
    @ResponseBody
    @RequestMapping("/update")
    public BaseResult updateParkingLog(ParkingLog parkingLog){
        BaseResult result = new BaseResult();
        try {
            result = parkingLogService.updateParkingLog(parkingLog);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("修改车记录失败");
        }
        return result;
    }

}
