package com.parkingmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

//停车记录
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class ParkingLog implements Serializable {


    private static final long serialVersionUID = -7180409357993188272L;

    private Integer parkingLogId;//
    private Integer parkingLogParkingId;// 停车场id
    private Integer parkingLogPlateId;// 车牌id
    private Integer parkingLogUserId;// 用户id
    private Long parkingLogStartTime;// 开始停车时间
    private Long parkingLogEndTime;// 结束停车时间
    private Double parkingLogPayment;//  费用

}



