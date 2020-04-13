package com.parkingmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

//停车场
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class Parking {

    private Integer parkingId;//
    private String parkingName;//
    private Integer parkingStatus = 0;//0 零为使用中  1为禁用  2为满员
    private Integer parkingCarportSize;// 停车场最大容量
    private Integer parkingCarportUsed = 0;// 停车场使用车位数量
    private Integer parking = 0;// 停车场使用车位数量
    private Double parkingPrice ; //单价

}
