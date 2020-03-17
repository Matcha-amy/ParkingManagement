package com.parkingmanagement.entity.system;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class Permissions implements Serializable {
    private Integer permissionsId;
    private String permissionsName;
    private String permissionsCode;


}
