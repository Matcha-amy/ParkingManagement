package com.parkingmanagement.controller;

import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.SecurityUtils;
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

    @RequiresRoles("admin")
    @RequestMapping(value = "/toAdminOrder")
    public String toAdminOrder(){
        return "/base/admin/orderLog.html";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/toUserOrder")
    public String toUserOrder(){
        return "/base/user/order.html";
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

    @ResponseBody
    @RequestMapping("/add")
    public BaseResult addOrder(OrderVO orderVO){
        BaseResult baseResult = new BaseResult();
        try {
            baseResult = orderService.addOrder(orderVO);
        }catch (Exception e){
            e.printStackTrace();
            return baseResult.setMsg("添加失败");
        }
        return baseResult;
    }


    //预约完成
    @ResponseBody
    @RequestMapping("/update")
    public BaseResult updateOrder(OrderVO orderVO){
        BaseResult baseResult = new BaseResult();
        try {
            baseResult = orderService.updateOrder(orderVO);
        }catch (Exception e){
            e.printStackTrace();
            return baseResult.setMsg("完成预约失败");
        }
        return baseResult;
    }


    @ResponseBody
    @RequestMapping("/userList")
    public List<OrderVO> getUserList(){
        List<OrderVO> orderVOList = new ArrayList<>();
        try {
            String userName=(String) SecurityUtils.getSubject().getPrincipal();
            orderVOList = orderService.getUserList(userName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return orderVOList;
    }

}
