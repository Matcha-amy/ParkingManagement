package com.parkingmanagement.utils;

public class Constant {
    //角色身份
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    //预约车位 每个月违约最大数
    public static final Integer BRESK_ORDER_MAX = 3;

    //预约车位状态
    //预约中
    public static final Integer ORDER_STATUS_ING=0;
    //已执行预约
    public static final Integer ORDER_STATUS_SUCCESS=1;
    //违约
    public static final Integer ORDER_STATUS_FAILED=2;

    //车位状态
    public static final Integer CARPORT_STATUS_FREE=0;
    public static final Integer CARPORT_STATUS_ORDER=2;
    public static final Integer CARPORT_STATUS_USEING=1;


}
