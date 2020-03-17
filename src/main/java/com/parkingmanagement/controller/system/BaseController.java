package com.parkingmanagement.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/base")
public class BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "/base/login.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister() {
        return "/base/register.html";
    }

//    @RequestMapping(value = "/home",method = RequestMethod.GET)
//    public String toRegister(){
//        return "/base/home.html";
//    }
}
