package com.parkingmanagement.dao;



import com.parkingmanagement.entity.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    List<User> getList();

    List<User> query(Map<String,Object> queryMap);

    void save(User user);

    Integer update(User user);

    User getUserById (Integer userId);
}
