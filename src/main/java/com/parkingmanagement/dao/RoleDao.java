package com.parkingmanagement.dao;

import com.parkingmanagement.entity.system.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDao {

     Role getRole(@Param("roleId") Integer roleId);
}
