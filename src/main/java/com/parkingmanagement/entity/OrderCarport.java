package com.parkingmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

//预约车位
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class OrderCarport implements Serializable {
    private static final long serialVersionUID = -4974031852004134565L;

    private Integer orderCarportId;

    private Integer userId;

    private Integer carportId;

    private Integer orderTime;

    private Integer orderStatus = 0;  // 0为 预约中   1为预约正常结束  2为预约超时

}
