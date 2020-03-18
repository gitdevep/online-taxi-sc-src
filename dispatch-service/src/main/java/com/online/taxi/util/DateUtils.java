package com.online.taxi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @date 2018/8/15
 */
public class DateUtils {
    public final static String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String yyMMddHHmm = "yyyy-MM-dd HH:mm";
    public final static String yyyyMMddHHmm = "yyyy年MM月dd日 HH:mm";

    public final static String DATE_FORMAT_1 = "yyyy-MM-dd";

    public final static String TIME_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public final static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public final static String MMDD = "MM月dd日";

    public final static String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

    public final static String MM_DD_HH = "MM月dd日HH点";

    public final static DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

    /**
     * 年月日时分秒格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_YMDHMS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 年月日格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_YMD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 时分秒格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_HMS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss");
        }
    };

    /**
     * 时分格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_HM = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    /**
     * 时分格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_MD = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };

    /**
     * 年月日时分秒毫秒格式化
     **/
    private static final ThreadLocal<SimpleDateFormat> fmt_YMDHMSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }
    };

    /**
     * 声明的日期字段
     *
     * @author gexu
     */
    public static enum DateFiled {
        Date_MilliSecond(Calendar.MILLISECOND), Date_Second(Calendar.SECOND), Date_Minute(Calendar.MINUTE), Date_Hour_OF_Day(Calendar.HOUR_OF_DAY), Date_Day(Calendar.DAY_OF_MONTH), Date_Month(Calendar.MONTH), Date_Year(Calendar.YEAR);

        public final int field;

        DateFiled(int field) {
            this.field = field;
        }
    }

    public static Date todayWithOutTime() {
        return truncDate(new Date());
    }

    /**
     * 截断日期，保留到日期
     *
     * @param date
     */
    public static Date truncDate(Date date) {
        return truncDate(date, DateFiled.Date_Day);
    }

    /**
     * 按照指定字段进行日期截断操作，保留到指定的字段
     *
     * @param date
     * @param field
     */
    public static Date truncDate(Date date, DateFiled field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 按照声明顺序进行迭代
        for (DateFiled f : DateFiled.values()) {
            if (f == field) {
                break;
            }
            cal.set(f.field, 0);
        }
        date.setTime(cal.getTimeInMillis());
        return date;
    }

    /**
     * 日期计算，通过Calendar处理
     *
     * @param original
     * @param calendarFieldType
     * @param amount
     * @return
     */
    public static Date add(Date original, int calendarFieldType, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(original);
        cal.add(calendarFieldType, amount);
        return cal.getTime();
    }

    public static String formatCurrentDate(String fmt) {
        return formatDate(new Date(), fmt);
    }

    public static Calendar getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal;
    }

    public static String formatDate(Calendar calendar, String fmt) {
        return formatDate(calendar.getTime(), fmt);
    }

    public static String formatDate(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    public static Date parseDate(String fmt, String dateStr) {
        return parseDate(fmt, dateStr, null);
    }

    public static Date parseDate(String fmt, String dateStr, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt, locale);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException(String.format("分析时间日期字符串异常,fmt[%s],locale[%s],dateStr[%s]", fmt, locale, dateStr), e);
        }
    }

    public static java.sql.Date changeToSQLDate(Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return sqlDate;
    }

    public static java.sql.Date changeToSQLDate(String source, String format) {
        return new java.sql.Date(parseDate(format, source).getTime());
    }

    public static java.sql.Timestamp changeToSQLTimestamp(String source, String format) {
        return new java.sql.Timestamp(parseDate(format, source).getTime());
    }

    public static java.sql.Timestamp changeToSQLTimestamp(Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    /**
     * 获取endDate与beginDate之间的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getSubDays(Date beginDate, Date endDate) {
        long times = endDate.getTime() - beginDate.getTime();
        return (int) (times / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取endDate与beginDate之间的分钟数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getSubMinutes(Date beginDate, Date endDate) {
        long times = endDate.getTime() - beginDate.getTime();
        return (int) (times / (1000 * 60));
    }

    /**
     * 获取endDate与beginDate之间的毫秒数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getSubMsec(Date beginDate, Date endDate) {
        long times = 0L;
        if (beginDate != null && endDate != null) {
            times = endDate.getTime() - beginDate.getTime();
        }
        return times;
    }

    /**
     * 获取endDate与beginDate之间时间差
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static String getSubTime(Date beginDate, Date endDate) {
        long times = endDate.getTime() - beginDate.getTime();
        int hh = (int) times / (1000 * 60 * 60);
        int mm = (int) times % (1000 * 60 * 60) / (1000 * 60);
        int ss = (int) times % (1000 * 60 * 60) % (1000 * 60) / 1000;
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * 通过长整型计算持续时间
     *
     * @param duration
     * @return
     */
    public static String calculateDuration(long duration) {
        StringBuilder dur = new StringBuilder();

        dur.insert(0, duration % 1000 + "毫秒");
        duration = duration / 1000;
        if (duration <= 0) {
            return dur.toString();
        }

        dur.insert(0, duration % 60 + "秒");
        duration = duration / 60;
        if (duration <= 0) {
            return dur.toString();
        }

        dur.insert(0, duration % 60 + "分");
        duration = duration / 60;
        if (duration <= 0) {
            return dur.toString();
        }

        dur.insert(0, duration + "小时");
        return dur.toString();
    }

    /**
     * 转换为中文日期表示
     *
     * @param date
     * @return
     */
    public static String changeToLocalString(Date date) {
        return df.format(date);
    }

    /**
     * 获得几天前的日期
     *
     * @param days
     * @return
     */
    public static String getBeforeDate(Integer days) {
        return formatDate(new Date(System.currentTimeMillis() - days * 24 * 60 * 60 * 1000L), "yyyy-MM-dd");
    }

    /**
     * 获得几天前的日期
     *
     * @param days
     * @return
     */
    public static String getBeforeDates(Integer days) {
        return formatDate(new Date(System.currentTimeMillis() - days * 24 * 60 * 60 * 1000L), "yyyy_MM_dd");
    }

    /**
     * 获得几天前的日期
     *
     * @param days
     * @param formate
     * @return
     */
    public static String getBeforeDay(Integer days, String formate) {
        return formatDate(new Date(System.currentTimeMillis() - days * 24 * 60 * 60 * 1000L), formate);
        // return formatDate(new Date(System.currentTimeMillis()), formate);
    }

    /**
     * 获取当前时间 小时
     *
     * @return
     */
    public static int currentHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * 获取当前分钟数
     *
     * @return
     */
    public static int currentMinute() {
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        return minute;
    }

    /**
     * 根据毫秒数计算时分
     *
     * @param log
     * @param flag
     * @return 该值根据log决定，当log以毫秒为单位时，flag=false,当log以秒为单位，false=true
     */
    public static String formatLongToTime(Long log, boolean flag) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (!flag) {
            second = log.intValue() / 1000;
        } else {
            second = log.intValue();
        }
        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute));
    }

    public static String getTwoLength(int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    /**
     * 格式化日期 时分秒
     *
     * @param date
     * @return String
     */
    public static String getStrByHms(Date date) {
        return fmt_HMS.get().format(date);
    }

    /**
     * 格式化日期 时分
     *
     * @param date
     * @return String
     */
    public static String getStrByHm(String date) {
        return fmt_HM.get().format(date);
    }

    /**
     * 格式化日期 月 日
     *
     * @param date
     * @return String
     */
    public static String getStrByMd(Date date) {
        return fmt_MD.get().format(date);
    }

    /**
     * 格式化日期 年 月 日
     *
     * @param date
     * @return
     */
    public static String getStrByYmd(Date date) {
        return fmt_YMD.get().format(date);
    }

    /**
     * 格式化日期 时分
     *
     * @param date
     * @return String
     */
    public static String getStrByHm(Date date) {
        return fmt_HM.get().format(date);
    }

    /**
     * 格式化日期 时分秒
     *
     * @param date
     * @return String
     */
    public static Date getDateByHms(String date) throws ParseException {
        return fmt_HMS.get().parse(date);
    }

    /**
     * 格式化日期 时分
     *
     * @param date
     * @return String
     */
    public static Date getDateByHm(Date date) throws ParseException {
        return fmt_HM.get().parse(fmt_HM.get().format(date));
    }

    /**
     * 返回精确到毫秒的当前时间
     *
     * @return
     */
    public static String getCurrentByYmdHmsSSS() {
        return fmt_YMDHMSS.get().format(new Date());
    }

    /**
     * 返回精确到秒的当前时间
     *
     * @return
     */
    public static String getCurrentByYmdHms() {
        return fmt_YMDHMS.get().format(new Date());
    }

    /**
     * 返回精确到天的当前时间
     *
     * @return
     */
    public static String getCurrentByYmd() {
        return fmt_YMD.get().format(new Date());
    }

    /**
     * 返回精确到秒的当前时间
     *
     * @return
     */
    public static String getCurrentByYmdHms(Date date) {
        return fmt_YMDHMS.get().format(date);
    }

    /**
     * 凌晨
     *
     * @param date
     * @return
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     * 1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        // 时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        // 凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            // 凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    /**
     * long to String
     *
     * @param lng
     * @param
     * @return HH:MM 格式
     */
    public static String longToString(Long lng) {
        Date date = new Date(lng);
        return fmt_HM.get().format(date);
    }

    /**
     * long时间
     *
     * @return
     */
    public static Long getAssignTime(int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, day);
        calendar.set(calendar.HOUR, hour);
        calendar.set(calendar.MINUTE, minute);
        calendar.set(calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * long时间
     *
     * @param date
     * @param num
     * @return
     */
    public static String longToStrByHHmm(Long date, int num) {
        int hour = 0;
        int minute = 0;
        hour = (int) (date / num);
        minute = (int) (date % num) + 1;
        return hour + " 小时 " + minute + " 分";
    }

    /**
     * @param date
     * @return
     */
    public static Boolean isToday(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        // 格式化为相同格式
        if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDayString(Date startDate) {
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (startDate.getTime() + offSet) / 86400000;
        int sub = (int) (start - today);
        //-2:前天             -1：昨天            0：今天             1：明天             2：后天

        if (sub == 0) {
            String dateStr = getStrByHm(startDate);
            return "今天" + dateStr;
        } else if (sub == 1) {
            String dateStr = getStrByHm(startDate);
            return "明天" + dateStr;
        } else {
            return getStrByYmd(startDate) + " " + getStrByHm(startDate);
        }
    }

    /**
     * 获取两个日期之间的日期
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.set(Calendar.HOUR_OF_DAY, 0);
        tempEnd.set(Calendar.MINUTE, 0);
        tempEnd.set(Calendar.SECOND, 0);
        tempEnd.set(Calendar.MILLISECOND, 0);
        while (tempStart.before(tempEnd)) {

            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}
