package com.parkingmanagement.service;

import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.utils.BaseResult;

public interface LoginService {

    BaseResult login(User user);

}
