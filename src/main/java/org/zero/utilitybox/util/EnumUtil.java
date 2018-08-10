package org.zero.utilitybox.util;


import org.zero.utilitybox.common.enums.CodeEnum;

/**
 * @ProjectName zhaxinle
 * @Author: zeroJun
 * @Date: 2018/8/7 12:14
 * @Description: 枚举工具类
 */
public class EnumUtil {

    /**
     * 通过code来获取相应的枚举
     *
     * @param code code
     * @param enumClass 枚举的类型
     * @param <T> <T>
     * @return T
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        // 通过传入的枚举类来遍历出该枚举类里的所有枚举
        for (T t : enumClass.getEnumConstants()) {
            // 如果参数code与枚举中的code一致，则返回该枚举
            if (code.equals(t.getCode())) {
                return t;
            }
        }
        return null;
    }
}
