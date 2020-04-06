package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.RoleDao;
import com.parkingmanagement.entity.system.Role;
import com.parkingmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role getRole(Integer roleId){
        return roleDao.getRole(roleId);
    }
}
