package com.parkingmanagement.service;


import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.utils.BaseResult;

import java.util.List;

public interface UserService {
    public User getUser(String username, String password);
    public User getUserByUsername(String username);
    public List<User> getList();

    BaseResult save(User user);
}
