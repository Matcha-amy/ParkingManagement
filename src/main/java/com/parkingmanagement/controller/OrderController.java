package com.parkingmanagement.controller;

import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/base/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/toOrder")
    public String toParking(){
        return "/base/admin/orderLog.html";
    }

    @ResponseBody
    @RequestMapping("/list")
    public List<OrderVO> getList(){
        List<OrderVO> orderVOList = new ArrayList<>();
        try {
            orderVOList = orderService.getList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return orderVOList;
    }



}
