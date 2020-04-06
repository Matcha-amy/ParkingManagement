package com.parkingmanagement.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.LoginService;
import com.parkingmanagement.service.RoleService;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.Captcha;
import com.parkingmanagement.utils.Constant;
import com.parkingmanagement.utils.GifCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping("/userLogin")
    public String userLogin(User user){
        BaseResult result =  new BaseResult();
        try {
            result = loginService.login(user);

        }catch(UnknownAccountException uae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            result.setMsg("未知账户");
        }catch(IncorrectCredentialsException ice){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            result.setMsg("密码不正确");
        }catch(LockedAccountException lae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            result.setMsg( "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            result.setMsg("用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
//            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            result.setMsg( "用户名或密码不正确");
        }
        return JSONObject.toJSONString(result);
    }

    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.removeAttribute("user");
        return "/base/login";
    }


}
