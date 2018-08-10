package org.zero.utilitybox.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ProjectName zhaxinle
 * @Author: zeroJun
 * @Date: 2018/8/6 18:14
 * @Description:
 */

@AllArgsConstructor
@Getter
public enum GameTitle implements CodeEnum{

    /**
     * 1-4关称号
     */
    V("无所畏惧", "V", 4),

    /**
     * 5-9关称号
     */
    IV("剑拔弩张", "IV", 9),

    /**
     * 10-15关称号
     */
    III("暗箭伤人", "III", 15),

    /**
     * 16-21关称号
     */
    II("众矢之的", "II", 21),

    /**
     * 22-27关称号
     */
    I("万箭穿心", "I", 27),

    /**
     * 28-30关称号
     */
    O("扎心了老铁", "O", 30),
    ;

    private String name;
    private String remark;
    private Integer code;
}
