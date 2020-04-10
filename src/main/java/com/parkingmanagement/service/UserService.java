package com.parkingmanagement.service;


import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.PageList;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    public User getUser(String username, String password);
    public User getUserByUsername(String username);
    public List<User> getList();
    List<User> query(HashMap<String,Object> queryMap);
    BaseResult register(User user);

    PageList getPageList(Integer pageNum, Integer pageSize,HashMap<String,Object> queryMap);

    BaseResult updateUser(User user);

    BaseResult orderCarport(OrderCarport user);
}
