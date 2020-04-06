package com.parkingmanagement.service;


import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.utils.BaseResult;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    public User getUser(String username, String password);
    public User getUserByUsername(String username);
    public List<User> getList();
    List<User> query(HashMap<String,Object> queryMap);
    BaseResult register(User user);
}
