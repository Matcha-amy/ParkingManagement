package com.parkingmanagement;

import com.parkingmanagement.entity.OrderCarport;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.service.OrderService;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.MD5Utils;
import com.parkingmanagement.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class ParkingmanagementApplicationTests {
    @Autowired
   private  UserService userService;
    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() throws InterruptedException {
        List<OrderVO> xiaohei =
                orderService.getUserList("xiaohei");
        System.out.println(xiaohei);
    }

}
