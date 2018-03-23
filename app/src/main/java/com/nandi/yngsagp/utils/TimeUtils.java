package com.nandi.yngsagp.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qingsong on 2018/3/21.
 */

public class TimeUtils {


    private static SimpleDateFormat simpleDateFormat;

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 根据毫秒时间戳来格式化字符串
     * 今天显示今天、昨天显示昨天、前天显示前天.
     * 早于前天的显示具体年-月-日，如2017-06-12；
     * @param timeStamp 毫秒值
     * @return 今天 昨天 前天 或者 yyyy-MM-dd HH:mm:ss类型字符串
     */
    public static String format(long timeStamp) {
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if(timeStamp >= todayStartMillis) {
            return "今天";
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMilis = todayStartMillis - oneDayMillis;
        if(timeStamp >= yesterdayStartMilis) {
            return "昨天";
        }
        long yesterdayBeforeStartMilis = yesterdayStartMilis - oneDayMillis;
        if(timeStamp >= yesterdayBeforeStartMilis) {
            return "前天";
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(new Date(timeStamp));
    }

    /**
     * 根据时间戳来判断当前的时间是几天前,几分钟,刚刚
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time){
        String long_by_13="1000000000000";
        String long_by_10="1000000000";
        if(Long.valueOf(long_time)/Long.valueOf(long_by_13)<1){
            if(Long.valueOf(long_time)/Long.valueOf(long_by_10)>=1){
                long_time=long_time+"000";
            }
        }
        Timestamp time=new Timestamp(Long.valueOf(long_time));
        Timestamp now=new Timestamp(System.currentTimeMillis());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    System.out.println("传递过来的时间:"+format.format(time));
//    System.out.println("现在的时间:"+format.format(now));
        long day_conver=1000*60*60*24;
        long hour_conver=1000*60*60;
        long min_conver=1000*60;
        long time_conver=now.getTime()-time.getTime();
        long temp_conver;
//    System.out.println("天数:"+time_conver/day_conver);
        if((time_conver/day_conver)<3){
            temp_conver=time_conver/day_conver;
            if(temp_conver<=2 && temp_conver>=1){
                return temp_conver+"天前";
            }else{
                temp_conver=(time_conver/hour_conver);
                if(temp_conver>=1){
                    return temp_conver+"小时前";
                }else {
                    temp_conver=(time_conver/min_conver);
                    if(temp_conver>=1){
                        return temp_conver+"分钟前";
                    }else{
                        return "刚刚";
                    }
                }
            }
        }else{
            return simpleDateFormat.format(time);
        }
    }
}
