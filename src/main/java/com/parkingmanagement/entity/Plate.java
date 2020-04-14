package com.parkingmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

//车牌
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class Plate implements Serializable {
    private static final long serialVersionUID = 8668975449486814942L;
    private Integer  plateId;//
    private Integer  userId;//
    private String  plateCode;//
    private Integer  plateStatus =0;//0为 使用  1为禁用

}
