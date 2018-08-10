package org.zero.utilitybox.util;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/7/31 14:03
 * @Description: id工具类
 */
public class IdUtil {

    /**
     * 生成id，使用当前年份+系统时间戳+2位随机数作为生成规则
     *
     * @return String
     */
    public static String getId() {
        DateTime dateTime = new DateTime();

        return String.valueOf(dateTime.getYear()) + System.currentTimeMillis() + new Random().nextInt(100);
    }
}
