package com.parkingmanagement.dao;



import com.parkingmanagement.entity.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    public User getUser(@Param("username") String userName, @Param("password") String password);

    User getUserByUsername(@Param("username") String username);

    List<User> getList();

    List<User> query(Map<String,Object> queryMap);

    void save(User user);
}
