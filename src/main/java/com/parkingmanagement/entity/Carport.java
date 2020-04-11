package com.parkingmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
//车位
public class Carport {

    private Integer carportId;//
    private String carportNum;// 车位编号
    private Integer carportStatus =0;//0为空闲  1为使用  2为预约中 3为禁用
    private String carportPos;//车位位置
    private Integer parkingId;//停车场id
}
