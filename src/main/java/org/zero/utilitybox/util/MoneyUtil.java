package org.zero.utilitybox.util;

import java.math.BigDecimal;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/13 10:43
 * @Description: 处理金额的工具类
 */
public class MoneyUtil {

    private static final Double MONEY_RANGE = 0.01;

    /**
     * 元转分
     *
     * @param yuan 元
     * @return Integer
     */
    public static Integer yuan2Fen(Double yuan) {
        return new BigDecimal(String.valueOf(yuan)).movePointRight(2).intValue();
    }

    /**
     * 分转元
     *
     * @param fen 分
     * @return Double
     */
    public static Double fen2Yuan(Integer fen) {
        return new BigDecimal(fen).movePointLeft(2).doubleValue();
    }

    /**
     * 比较两个金额是否相等，金额单位：元
     *
     * @param d1 d1
     * @param d2 d2
     * @return Boolean
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);

        return result < MONEY_RANGE;
    }
}
