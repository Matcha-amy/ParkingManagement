package com.parkingmanagement.controller;

import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/toTest")
    public String toTest(ModelAndView modelAndView){
//        List<User> list = userService.getList();
//        System.out.println(list);
//        modelAndView.addObject("userlist",list);
        return "test";
    }
}
