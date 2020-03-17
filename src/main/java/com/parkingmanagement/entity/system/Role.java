package com.parkingmanagement.entity.system;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@Accessors(chain = true)
public class Role implements Serializable {
    private Integer roleId;
    private String roleName;
    private String roleCode;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

}
