package com.parkingmanagement.controller.system;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/base")
public class BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "/base/index.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegister() {
        return "/base/index.html";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String toError() {
        return "/base/error.html";
    }

    @ResponseBody
    @RequestMapping(value = "/getName", method = RequestMethod.POST)
    public String getName() {
        String userName=(String) SecurityUtils.getSubject().getPrincipal();
        return userName;
    }
}
