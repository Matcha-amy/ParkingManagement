package com.parkingmanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.entity.vo.PaymentVO;
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
@RequestMapping("/base/payment")
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
            List<PaymentVO> paymentList =  paymentService.getList(userName);
            return JSONObject.toJSONString(paymentList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }





}
