package com.parkingmanagement;

import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.MD5Utils;
import com.parkingmanagement.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ParkingmanagementApplicationTests {
    @Autowired
   private  UserService userService;


    @Test
    void contextLoads() throws InterruptedException {
        Long startTime = new Date().getTime();
        Thread.sleep(2000);
        Long endTime = new Date().getTime();
        Double distanceTime = TimeUtils.getStopCarTime(startTime, endTime);
        System.out.println(distanceTime);
    }

}
