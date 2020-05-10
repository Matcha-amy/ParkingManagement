package com.parkingmanagement.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    /*
     *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     * */
    public static Double getDistanceTime(long time1, long time2) {
        String  hourStr ;
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        hourStr = (diff / (60 * 60 * 1000))+"";
        Double hour = new Double(hourStr);
        return  Math.ceil(hour);
    }

    public static String  timeToStr(Long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date(time));
        return format;
    }

    public static Long strToTime(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse(str);
        return parse.getTime();
    }

    public static Double getStopCarTime(Long startTime, Long endTime){
        Long i =  endTime-startTime;
        BigDecimal time =  new BigDecimal(new Double(i));
        BigDecimal m =  new BigDecimal(3600);
        BigDecimal divide = time.divide(m,2,BigDecimal.ROUND_HALF_UP);
        double ceil = Math.ceil(divide.doubleValue());
        return  ceil;
    }
}
