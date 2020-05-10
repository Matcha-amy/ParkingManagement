package com.parkingmanagement.entity.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class OrderVO implements Serializable {
    private static final long serialVersionUID = -7199972497122266210L;

    private String username;//用户名
    private String plateCode;//车牌号
    private String parkingName;//
    private String carportNum;//车位编号
    private String orderCarportTime;//
    private Integer orderCarportStatus;//
    private Integer orderCarportId;
}
