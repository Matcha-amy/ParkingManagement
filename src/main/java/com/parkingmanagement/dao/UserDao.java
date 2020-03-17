package com.parkingmanagement.dao;



import com.parkingmanagement.entity.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    public User getUser(@Param("username") String userName, @Param("password") String password);

    User getUserByUsername(@Param("username") String username);

    List<User> getList();

    void save(User user);
}
