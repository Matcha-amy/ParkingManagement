package com.parkingmanagement.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String registerUser(User user){
        BaseResult result = new BaseResult();
        try {
             result = userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
            result.setMsg("注册失败，请重新注册");
        }
        return JSONObject.toJSONString(result);
    }
}
