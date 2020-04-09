package com.parkingmanagement.controller.system;

import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.PageList;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequiresRoles({"admin"})
    @RequestMapping(value = "/userList")
    public PageList getUserList(@RequestParam ("page")Integer pageNum, @RequestParam ("limit")Integer limit){
        PageList pageList = new PageList();
        try {
            HashMap<String,Object> queryHash =new HashMap<>();
            pageList = userService.getPageList(pageNum,limit,queryHash);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageList;
    }

    @ResponseBody
    @RequiresRoles({"admin"})
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public BaseResult updateUser(User user ){
        BaseResult result = new BaseResult();
        try{
            if (user == null || user.getUserId() == null){
                return result.setMsg("请选择更改的用户");
            }
            result = userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return result.setMsg("更改失败");
        }
        return result;
    }


    @RequiresRoles("admin")
    @RequestMapping("/index")
    public String toHome(){
        return "/base/admin/userCon.html";
    }

    @RequiresRoles("admin")
    @RequestMapping("/park")
    public String toPark(){
        return "/base/admin/park.html";
    }

    @RequiresRoles("admin")
    @RequestMapping("/charge")
    public String toCharge(){
        return "/base/admin/charge.html";
    }
}
