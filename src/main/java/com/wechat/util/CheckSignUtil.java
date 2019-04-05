package com.wechat.util;

import com.wechat.pojo.Point;
import com.wechat.pojo.SignTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CheckSignUtil {

    /**
     * 功能描述: 校验用户当天是否已经签到
     * 返回1代表已签到，0代表未签到且处于连续签到状态 2代表未签到但处于断续签到状态
     * @param date
     * @return
     * @throws Exception
     */
    public int checkAllotSigin(Date date) throws Exception {

        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将Date类型转换成String类型
        String time = sdf.format(date);
        System.out.println("转换后的时间:" + time);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.parse(time, dtf);
        System.out.println("当前的localTime:" + localTime);
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        System.out.println("startTime:" + startTime);
        LocalDateTime endTime = LocalDate.now().atTime(23, 59, 59);
        System.out.println("endTime:" + endTime);
        //如果小于今天的开始日期
        if (localTime.isBefore(startTime)) {

            /**判断是否小于昨天，小于昨天证明签到不连续，签到记录表签到连续次数设置为0*/
            Date newTime = new Date();
            //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = format.format(newTime);
            Date today = format.parse(todayStr);
            //昨天 86400000=24*60*60*1000 一天  大于昨天 至少为前天
            if ((today.getTime() - date.getTime()) > 86400000) {
                result = 2;
                System.out.println("小于今天的开始日期,至少为前天的时间,连续签到终止");
            } else {
                result = 0;
                System.out.println("小于今天的开始日期,最后一次签到是昨天，连续签到未终止");
            }
        }
        //如果大于今天的开始日期，小于今天的结束日期
        if (localTime.isAfter(startTime) && localTime.isBefore(endTime)) {
            System.out.println("大于今天的开始日期，小于今天的结束日期");
            result = 1;
        }
        //如果大于今天的结束日期
        if (localTime.isAfter(endTime)) {
            System.out.println("大于今天的结束日期");
            result = 1;
        }

        return result;
    }

    /**
     * 返回当天的开始时间
     * @return
     */
    public Date todayTime(){
        Point point = new Point();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime startTime = LocalDate.now().atTime(0, 0, 0);
        ZonedDateTime zdt1 = startTime.atZone(zoneId);
        Date date = Date.from(zdt1.toInstant());
        point.setSignTime(date);
        return point.getSignTime();
    }

}
