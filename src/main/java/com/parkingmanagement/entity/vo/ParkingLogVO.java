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
public class ParkingLogVO implements Serializable {
    private static final long serialVersionUID = 4459503435321082303L;
    private String username; //用户名
    private String plateCode; //车牌号
    private String parkingName; //停车场名
    private String parkingLogStartTime;
    private String parkingLogEndTime;
    private Double parkingLogPayment;
    private Integer parkingLogId;

}
