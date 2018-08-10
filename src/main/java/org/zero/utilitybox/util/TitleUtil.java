package org.zero.utilitybox.util;


import org.zero.utilitybox.common.enums.GameTitle;

/**
 * @ProjectName zhaxinle
 * @Author: zeroJun
 * @Date: 2018/8/9 10:33
 * @Description:
 */
public class TitleUtil {

    private static GameTitle[] gameTitles = GameTitle.values();

    private static int[] arr = new int[gameTitles.length];

    static {
        for (int i = 0; i < gameTitles.length; i++) {
            arr[i] = gameTitles[i].getCode();
        }
    }

    /**
     * 根据score获取相应的title code
     *
     * @param score score
     * @return int
     */
    public static int getTitleCode(int score) {
        if (score <= 0) {
            return -1;
        }

        for (int i : arr) {
            if (score <= i) {
                return i;
            }
        }
        return -1;
    }
}
