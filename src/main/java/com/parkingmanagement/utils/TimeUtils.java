package com.parkingmanagement.utils;

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String format = simpleDateFormat.format(new Date(time));
        return format;
    }

}
