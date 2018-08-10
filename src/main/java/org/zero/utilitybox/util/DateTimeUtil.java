package org.zero.utilitybox.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/7/31 11:06
 * @Description: 格式化时间日期，以及完成类型之间的转换
 */
public class DateTimeUtil {

    private static final String STANDARO_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串转Date类型
     *
     * @param dateTimeStr 时间（字符串）
     * @param formatStr   需要转换的格式
     * @return Date
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);

        return dateTime.toDate();
    }

    /**
     * Date转字符串类型
     *
     * @param date      时间（Date）
     * @param formatStr 需要转换的格式
     * @return String
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    /**
     * 字符串转Date类型，使用默认格式
     *
     * @param dateTimeStr 时间（字符串）
     * @return Date
     */
    public static Date strToDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARO_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);

        return dateTime.toDate();
    }

    /**
     * Date转字符串类型，使用默认格式
     *
     * @param date 时间（Date）
     * @return String
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARO_FORMAT);
    }

    /**
     * 获取当前时间与给定时间的相差天数
     *
     * @param beforeDate beforeDate
     * @return long
     */
    public static long getDifferDays(Date beforeDate) {
        DateTime begin = new DateTime(beforeDate);
        DateTime now = new DateTime();
        Duration duration = new Duration(begin, now);

        return duration.getStandardDays();
    }
}
