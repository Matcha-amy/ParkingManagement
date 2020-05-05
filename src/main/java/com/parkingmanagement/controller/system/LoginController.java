package com.parkingmanagement.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.LoginService;
import com.parkingmanagement.utils.BaseResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping("/userLogin")
    public String userLogin(User user){
        BaseResult result =  new BaseResult();
        try {
            result = loginService.login(user);
        }catch(UnknownAccountException uae){
            result.setMsg("未知账户");
        }catch(IncorrectCredentialsException ice){
            result.setMsg("密码不正确");
        }catch(LockedAccountException lae){
            result.setMsg( "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            result.setMsg("用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            ae.printStackTrace();
            result.setMsg( "用户名或密码不正确");
        }
        return JSONObject.toJSONString(result);
    }

    @RequestMapping("/logOut")
    public void logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }


}
