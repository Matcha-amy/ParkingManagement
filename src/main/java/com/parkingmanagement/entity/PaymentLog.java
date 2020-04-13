package com.parkingmanagement.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class PaymentLog implements Serializable {
    private static final long serialVersionUID = 3779479235003204574L;

    private Integer paymentId;//
    private Integer paymentTime;//
    private Integer paymentMoney;//
    private Integer paymentUserId;//

}
