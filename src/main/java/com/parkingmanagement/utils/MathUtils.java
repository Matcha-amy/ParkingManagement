package com.parkingmanagement.utils;

import java.math.BigDecimal;

public class MathUtils {

    public static   Double doubleMul(Double d1, Double d2){
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        BigDecimal multiply = bd1.multiply(bd2);
        return multiply.doubleValue();
    }
}
