package com.parkingmanagement.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    //用户主页
    @RequiresRoles({"user"})
    @RequestMapping(value = "/index")
    public String toIndex(HttpServletRequest request, String username){
        request.setAttribute("username",username);
        return "/base/user/index.html";
    }

    //注册
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerUser(User user){
        BaseResult result = new BaseResult();
        try {
             result = userService.register(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("注册失败，请重新注册");
        }
        return JSONObject.toJSONString(result);
    }

    //预约
    @ResponseBody
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public String orderCarport(OrderCarport orderCarport){
        BaseResult result = new BaseResult();
        try {
            result = userService.orderCarport(orderCarport);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("预约失败，请重新预约");
        }
        return JSONObject.toJSONString(result);
    }

    //用户个人信息修改
    @ResponseBody
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public BaseResult updateUser(User user){
        BaseResult result = new BaseResult();
        try {
            result = userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("用户更改失败，请重新修改");
        }
        return result;
    }

    //用户禁用
    @RequiresRoles({"admin"})
    @ResponseBody
    @RequestMapping(value = "/delUser",method = RequestMethod.POST)
    public BaseResult delUser(User user){
        BaseResult result = new BaseResult();
        try {
            result = userService.delUser(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("用户禁用失败，请重新禁用");
        }
        return result;
    }


}
