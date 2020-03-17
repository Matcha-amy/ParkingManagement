package com.parkingmanagement.controller.system;

import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addUser(User user){
        try {
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();

        }
        return "login";
    }
}
