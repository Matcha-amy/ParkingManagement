package com.parkingmanagement;

import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkingmanagementApplicationTests {

    @Test
    void contextLoads() {
        User  u  = new User();
        u.setUsername("root");
        u.setPassword("proot");
        String pas = MD5Utils.encodePassword(u.getPassword(), u.getCredentialsSalt());
        System.out.println(pas);
    }

}
