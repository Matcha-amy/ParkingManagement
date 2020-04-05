package com.parkingmanagement.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回结果类
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 6432524051796167225L;

    private Boolean status = false;

    private String msg;

    private Integer code;

    private Object data;


}
