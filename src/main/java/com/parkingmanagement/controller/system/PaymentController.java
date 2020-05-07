package com.parkingmanagement.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.service.PaymentService;
import com.parkingmanagement.service.PlateService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/toPayment")
    public String toPlate(){
        return "/base/user/payment.html";
    }


    @RequiresRoles({"user"})
    @ResponseBody
    @RequestMapping(value = "/list")
    public String getList(){
        try {
            String userName=(String) SecurityUtils.getSubject().getPrincipal();
            List<Payment> paymentList =  PaymentService.getList(userName);
            return JSONObject.toJSONString(paymentList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //修改
    @RequiresRoles({"user","admin"})
    @ResponseBody
    @RequestMapping(value = "/updatePlate",method = RequestMethod.POST)
    public BaseResult updatePlate(Plate plate){
        BaseResult baseResult = new BaseResult();
        try{
           baseResult = pleateService.updatePlate(plate);
        }catch (Exception e){
            e.printStackTrace();
            baseResult.setMsg("修改车牌信息失败，请重新修改");
        }
        return baseResult;
    }

    //添加
    @ResponseBody
    @RequestMapping(value = "/addPlate",method = RequestMethod.POST)
    public BaseResult addPlate(Plate plate){
        BaseResult baseResult = new BaseResult();
        try{
            baseResult = pleateService.addPlate(plate);
        }catch (Exception e){
            e.printStackTrace();
            baseResult.setMsg("添加车牌信息失败，请重新添加");
        }
        return baseResult;
    }



}
