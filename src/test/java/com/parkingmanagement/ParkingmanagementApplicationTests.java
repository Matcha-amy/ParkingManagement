package com.parkingmanagement;

import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkingmanagementApplicationTests {
    @Autowired
   private  UserService userService;


    @Test
    void contextLoads() {
        OrderCarport orderCarport = new OrderCarport();
        orderCarport.setOrderCarportUserId(1).setOrderCarportCarportId(1);
        BaseResult result = userService.orderCarport(orderCarport);
        System.out.println(result);
    }

}
