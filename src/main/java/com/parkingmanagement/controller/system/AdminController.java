package com.parkingmanagement.controller.system;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequiresRoles("admin")
    @RequestMapping("/index")
    public String toHome(){
        return "/base/admin/userCon.html";
    }
}
