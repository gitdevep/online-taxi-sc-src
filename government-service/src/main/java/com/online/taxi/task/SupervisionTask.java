package com.online.taxi.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 上报任务的基础接口
 *
 * @date 2018/8/28
 */
public interface SupervisionTask {

    /**
     * 时间日期格式枚举体
     */
    @Getter
    @AllArgsConstructor
    enum DateTimePatternEnum {
        /**
         * yyyyMMddHHmmss
         */
        DateTime("yyyyMMddHHmmss"),
        /**
         * yyyyMMdd
         */
        Date("yyyyMMdd"),
        /**
         * yyyyMM
         */
        Month("yyyyMM"),

        /**
         * HHmmss
         */
        Time("HHmmss");

        private String pattern;
    }

    /**
     * 返回当天时间
     *
     * @return yyyyMMddHHmmss
     */
    default long now() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateTimePatternEnum.DateTime.getPattern());
        return Long.parseLong(LocalDateTime.now().format(fmt));
    }

    /**
     * 返回当天
     *
     * @return yyyyMMdd
     */
    default long today() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateTimePatternEnum.Date.getPattern());
        return Long.parseLong(LocalDateTime.now().format(fmt));
    }

    /**
     * 返回当月
     *
     * @return yyyyMM
     */
    default long currentMonth() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(DateTimePatternEnum.Month.getPattern());
        return Long.parseLong(LocalDateTime.now().format(fmt));
    }

    /**
     * 将输入的对象转换为不带符号的数字
     *
     * @param date 日期对象
     * @return 日期对象所对应的数字
     */
    default long trimDate(Object date) {
        return Long.parseLong(("" + date).replaceAll("[^0-9]", ""));
    }

    /**
     * 按照指定格式将日期转为long
     *
     * @param date        日期
     * @param patternEnum 格式
     * @return 指定格式的日期数字
     */
    default long formatDateTime(Date date, DateTimePatternEnum patternEnum) {
        LocalDateTime dt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Long.parseLong(DateTimeFormatter.ofPattern(patternEnum.getPattern()).format(dt));
    }

    /**
     * 转换坐标
     *
     * @param coordinate 坐标
     * @return 转换后坐标
     */
    default long toCoordinates(String coordinate) {
        return new BigDecimal(coordinate).multiply(new BigDecimal("1000000")).longValue();
    }
}
