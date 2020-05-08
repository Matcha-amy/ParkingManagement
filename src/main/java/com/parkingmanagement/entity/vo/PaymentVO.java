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
public class PaymentVO implements Serializable {
    private String paymentTime;//
    private Double paymentMoney;//

}
