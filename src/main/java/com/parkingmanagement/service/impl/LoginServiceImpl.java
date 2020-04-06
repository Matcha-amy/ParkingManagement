package com.parkingmanagement.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.LoginService;
import com.parkingmanagement.service.RoleService;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public BaseResult login(User user) {
        BaseResult result =  new BaseResult();
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);   //完成登录
        String  loginUsername=(String ) subject.getPrincipal();
        HashMap<String,Object> queryHash = new HashMap<>();
        queryHash.put("username",loginUsername);
        List<User> users = userService.query(queryHash);
        if (users == null|| users.isEmpty()){
            return  result.setMsg("用户不存在，请重新输入");
        }
        Role role= roleService.getRole(users.get(0).getRoleId());
        if (role.getRoleCode().equals(Constant.ROLE_ADMIN)){
            result.setStatus(true).setCode(999);
        }else if (role.getRoleCode().equals(Constant.ROLE_USER)){
            result.setStatus(true).setCode(200);
        }
        return result;
    }
}
