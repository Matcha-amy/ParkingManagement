package com.parkingmanagement.service.impl;



import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User getUser(String username, String password) {
        return userDao.getUser(username,password);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao. getUserByUsername( username);
    }

    @Override
    public List<User> getList() {
        return userDao.getList();
    }

    @Override
    public void save(User user) {
        String pas = MD5Utils.encodePassword(user.getPassword(), user.getCredentialsSalt());
        user.setPassword(pas);
        System.out.println(pas);
        userDao.save(user);
    }
}
