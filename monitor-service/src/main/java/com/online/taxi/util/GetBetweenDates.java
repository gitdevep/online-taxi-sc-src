package com.online.taxi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取两个日期之间的日期
 *
 * @date 2018/11/22
 */

public class GetBetweenDates {

    /**
     * 获取两个日期字符串之间的日期集合
     * @param startTime:String
     * @param endTime:String
     * @return list:yyyy-MM-dd
     */
    public static List<String> getBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                list.add(sdf.format(startDate));
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 1);
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取两个日期之间所有的月份集合
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime){
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();
            min.setTime(sdf.parse(startTime));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(sdf.parse(endTime));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
