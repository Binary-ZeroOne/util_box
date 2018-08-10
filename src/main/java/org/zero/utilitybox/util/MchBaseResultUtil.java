package org.zero.utilitybox.util;

import weixin.popular.bean.paymch.MchBaseResult;
import weixin.popular.util.XMLConverUtil;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 10:23
 * @Description: 回复微信支付回调结果
 */
public class MchBaseResultUtil {

    /**
     * 回复SUCCESS
     *
     * @return MchBaseResult
     */
    private static MchBaseResult returnSuccess() {
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("SUCCESS");
        baseResult.setReturn_msg("OK");

        return baseResult;
    }

    /**
     * 回复FAIL
     *
     * @return MchBaseResult
     */
    private static MchBaseResult returnFail() {
        MchBaseResult baseResult = new MchBaseResult();
        baseResult.setReturn_code("FAIL");
        baseResult.setReturn_msg("ERROR");

        return baseResult;
    }

    public static String returnSuccessXml() {
        return XMLConverUtil.convertToXML(returnSuccess());
    }

    public static String returnFailXml() {
        return XMLConverUtil.convertToXML(returnFail());
    }
}
