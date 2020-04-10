package com.parkingmanagement.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class User implements Serializable {
    private Integer userId;
    private String username;
    private String password;
    private String salt ="8d78869f470951332959580424d4bf4f";//盐
    /**
     * 用户对应的角色集合
     */
    private Integer roleId;

    /**
     * 密码盐.
     * @return
     */
    public String getCredentialsSalt(){
        return this.username+this.salt;
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }
}
