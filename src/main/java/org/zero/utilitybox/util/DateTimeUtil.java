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

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final int FIFTY_NINE = 59;

    private static final int TWENTY_THREE = 23;
    
    /**
     * 判断是否是标准的时间格式
     *
     * @param dateTimeStr dateTimeStr
     * @return boolean
     */
    public static boolean isStandardDate(String dateTimeStr) {
        return isParser(dateTimeStr, STANDARO_FORMAT);
    }

    /**
     * 判断是否是指定的时间格式
     *
     * @param dateTimeStr dateTimeStr
     * @return boolean
     */
    public static boolean isParser(String dateTimeStr, String formatStr) {
        try {
            strToDateTime(dateTimeStr, formatStr);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    /**
     * 字符串转DateTime类型
     *
     * @param dateTimeStr 时间（字符串）
     * @param formatStr   需要转换的格式
     * @return DateTime
     */
    public static DateTime strToDateTime(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);

        return dateTimeFormatter.parseDateTime(dateTimeStr);
    }

    /**
     * 字符串转DateTime类型，使用默认时间格式
     *
     * @param dateTimeStr 时间（字符串）
     * @return DateTime
     */
    public static DateTime strToDateTime(String dateTimeStr) {
        return strToDateTime(dateTimeStr, STANDARO_FORMAT);
    }
    
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
    
    /**
     * 获取两个时间的相差天数
     *
     * @param beforeDate beforeDate
     * @return long
     */
    public static long getDifferDays(Date beforeDate, Date lastDate) {
        DateTime begin = new DateTime(beforeDate);
        DateTime last = new DateTime(lastDate);
        Duration duration = new Duration(begin, last);

        return duration.getStandardDays();
    }

    /**
     * 判断给定的日期是否早于当前日期
     *
     * @param date date
     * @return boolean
     */
    public static boolean isBeforeNow(Date date) {
        DateTime dateTime = new DateTime(date);

        return dateTime.isBeforeNow();
    }

    /**
     * 判断给定的日期是否早于当天日期
     *
     * @param date date
     * @return boolean
     */
    public static boolean isBeforeToday(Date date) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime dateTime = new DateTime(date);

        return dateTime.isBefore(today);
    }

    /**
     * 判断给定的日期是否早于本周
     *
     * @param date date
     * @return boolean
     */
    public static boolean isBeforeWeek(Date date) {
        // 获取本周周一零点的时间
        DateTime monday = new DateTime().dayOfWeek().setCopy(1).withTimeAtStartOfDay();
        DateTime dateTime = new DateTime(date);

        return dateTime.isBefore(monday);
    }

    /**
     * 判断给定的日期是否早于本月
     *
     * @param date date
     * @return boolean
     */
    public static boolean isBeforeMonth(Date date) {
        // 获取本月一号
        DateTime firsOfMonth = new DateTime().dayOfMonth().setCopy(1).withTimeAtStartOfDay();
        DateTime dateTime = new DateTime(date);

        return dateTime.isBefore(firsOfMonth);
    }

    /**
     * 判断给定的日期是否是昨天
     *
     * @param date date
     * @return boolean
     */
    public static boolean isYesterday(Date date) {
        DateTime yesterday = new DateTime().minusDays(1).withTimeAtStartOfDay();
        DateTime dateTime = new DateTime(date).withTimeAtStartOfDay();

        return yesterday.isEqual(dateTime);
    }

    /**
     * 判断给定的日期是否是上一周的
     *
     * @param date date
     * @return boolean
     */
    public static boolean isLastWeek(Date date) {
        // 获取上周一
        DateTime monday = new DateTime().minusWeeks(1).dayOfWeek().setCopy(1).withTimeAtStartOfDay();
        // 获取上周日
        DateTime sunday = new DateTime().minusWeeks(1).dayOfWeek().setCopy(7)
                .withTime(TWENTY_THREE, FIFTY_NINE, FIFTY_NINE, 999);
        DateTime datetime = new DateTime(date);

        return isInRange(datetime, monday, sunday);
    }

    /**
     * 判断给定的日期是否是上个月的
     *
     * @param date date
     * @return boolean
     */
    public static boolean isLastMonth(Date date) {
        // 获取上月一号
        DateTime firsOfMonth = new DateTime().minusMonths(1).dayOfMonth().setCopy(1)
                .withTimeAtStartOfDay();
        // 获取上月末
        DateTime lastOfMonth = new DateTime().minusMonths(1).dayOfMonth()
                .withMaximumValue().withTime(TWENTY_THREE, FIFTY_NINE, FIFTY_NINE, 999);
        DateTime datetime = new DateTime(date);

        return isInRange(datetime, firsOfMonth, lastOfMonth);
    }

    /**
     * 减去时间，只保留日期
     *
     * @param dateTime dateTime
     * @return DateTime
     */
    public static DateTime subTime(DateTime dateTime) {
        String string = dateTime.toString(STANDARO_FORMAT);
        string = string.substring(0, string.indexOf(" "));

        return strToDateTime(string, DATE_FORMAT);
    }

    /**
     * 判断一个日期是否在一个给定的时间范围内
     *
     * @param dateTime dateTime
     * @param start    start
     * @param end      end
     * @return boolean
     */
    public static boolean isInRange(DateTime dateTime, DateTime start, DateTime end) {
        if (dateTime.isEqual(start) || dateTime.isEqual(end)) {
            return true;
        }

        return dateTime.isAfter(start) && dateTime.isBefore(end);
    }
}
