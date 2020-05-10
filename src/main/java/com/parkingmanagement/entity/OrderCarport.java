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

    private Integer orderCarportUserId;

    private Integer orderCarportCarportId; //车位id

    private Integer orderPlateId; //车牌id

    private Long orderCarportTime;

    private Integer orderCarportStatus = 0;  // 0为 预约中   1为预约正常结束  2为预约超时

}
