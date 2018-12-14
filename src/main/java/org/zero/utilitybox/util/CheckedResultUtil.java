package org.zero.utilitybox.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zero.utilitybox.common.ServerResponse;
import org.zero.utilitybox.common.enums.ResponseEnum;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.paymch.MchBase;
import weixin.popular.bean.paymch.base.BillResult;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/6/28 11:36
 * @Description: 检查weixin.popular请求微信接口后返回的数据
 */
@Slf4j
public class CheckedResultUtil {

    /**
     * 商户订单已支付
     */
    private static final String ORDERPAID = "ORDERPAID";

    /**
     * 微信服务器系统繁忙
     */
    private static final String SYSTEMERROR = "SYSTEMERROR";

    /**
     * 指定单号数据不存在
     */
    private static final String NOT_FOUND = "NOT_FOUND";

    public static <T> ServerResponse<T> checkedGetResult(ResponseEnum successMsg, ResponseEnum errorMsg, T result) {
        if (result == null) {
            return ServerResponse.createByErrorMsg(ResponseEnum.OPERATION_RETRY.getMsg());
        }

        if (result instanceof BaseResult) {

            return checkedBaseResult(successMsg, errorMsg, result);
        } else if (result instanceof MchBase) {

            return checkedMchBase(successMsg, errorMsg, result);
        } else {
            log.error("【检查result数据】不被支持的实例对象, resultClassName: {}", result.getClass().getName());
            throw new IllegalArgumentException();
        }
    }

    /**
     * 检查微信支付的结果数据中是否包含错误码
     *
     * @param successMsg 成功消息
     * @param errorMsg   错误消息
     * @param mchResult  结果数据
     * @param <T>        泛型
     * @return ServerResponse<T>
     */
    private static <T> ServerResponse<T> checkedMchBase(ResponseEnum successMsg, ResponseEnum errorMsg, T mchResult) {

        MchBase mchBase = (MchBase) mchResult;

        if (StringUtils.isNotBlank(mchBase.getReturn_code()) && mchBase.getReturn_code().equals(ResponseEnum.SUCCESS.getMsg())) {

            if (StringUtils.isNotBlank(mchBase.getResult_code()) && mchBase.getResult_code().equals(ResponseEnum.SUCCESS.getMsg())) {
                log.info("【检查mchResult数据】successMsg: {}", successMsg.getMsg());
                return ServerResponse.createBySuccessMsg(successMsg.getMsg(), mchResult);
            } else if (StringUtils.isNotBlank(mchBase.getResult_code()) && mchBase.getResult_code().equals(ResponseEnum.FAIL.getMsg())) {
                // 判断是否是已支付订单
                if (mchBase.getErr_code().equals(ORDERPAID)) {
                    log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getErr_code(), mchBase.getErr_code_des());
                    return ServerResponse.createBySuccessMsg(successMsg.getMsg(), mchResult);
                }

                // 企业付款-当返回错误码为“SYSTEMERROR”时，请不要更换商户订单号，一定要使用原商户订单号重试，否则可能造成重复支付等资金风险
                if (mchBase.getErr_code().equals(SYSTEMERROR)) {
                    log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getErr_code(), mchBase.getErr_code_des());
                    String errMsg = StringUtils.join(errorMsg.getMsg(), mchBase.getErr_code_des(),
                            ", 请稍后使用原商户订单号重试, 请勿更换订单号, 否则可能造成重复支付等资金风险。若仍存在问题请及时联系客服确认付款情况 ");

                    return ServerResponse.createByErrorMsg(errMsg);
                }

                if (mchBase.getErr_code().equals(NOT_FOUND)) {
                    log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getErr_code(), mchBase.getErr_code_des());
                    String errMsg = StringUtils.join(errorMsg.getMsg(), mchBase.getErr_code_des(),
                            ", 请勿直接判断为付款失败, 先确认商户订单号是自己发起的, 然后隔几分钟再尝试查询。或通过相同的商户订单号再次发起付款, 否则可能造成资金损失, 若仍存在问题请及时联系客服确认付款情况 ");

                    return ServerResponse.createByErrorMsg(errMsg);
                }

                log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getErr_code(), mchBase.getErr_code_des());
                return ServerResponse.createByErrorMsg(errorMsg.getMsg() + mchBase.getErr_code_des());
            }

            log.info("【检查mchResult数据】successMsg: {}", successMsg.getMsg());
            return ServerResponse.createBySuccessMsg(successMsg.getMsg(), mchResult);
        } else if (StringUtils.isNotBlank(mchBase.getReturn_code()) && mchBase.getReturn_code().equals(ResponseEnum.FAIL.getMsg())) {

            log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getReturn_code(), mchBase.getReturn_msg());
            return ServerResponse.createByErrorMsg(errorMsg.getMsg() + mchBase.getReturn_msg());
        }

        if (mchResult instanceof BillResult) {
            return ServerResponse.createBySuccessData(mchResult);
        }

        log.error("【检查mchResult数据】code: {}, errorMsg: {}", mchBase.getReturn_code(), mchBase.getReturn_msg());
        return ServerResponse.createByErrorMsg(errorMsg.getMsg() + mchBase.getReturn_msg());
    }

    /**
     * 检查result数据中是否包含错误码
     *
     * @param successMsg 成功消息
     * @param errorMsg   错误消息
     * @param result     结果数据
     * @param <T>        泛型
     * @return ServerResponse<T>
     */
    private static <T> ServerResponse<T> checkedBaseResult(ResponseEnum successMsg, ResponseEnum errorMsg, T result) {
        BaseResult baseResult = (BaseResult) result;
        // 判断是否有错误码
        if (baseResult.getErrcode() == null || baseResult.getErrcode().equals(ResponseEnum.SUCCESS.getCode().toString())) {
            log.info("【检查result数据】successMsg: {}", successMsg.getMsg());
            return ServerResponse.createBySuccessMsg(successMsg.getMsg(), result);
        }

        log.error("【检查result数据】code: {}, errorMsg: {}", baseResult.getErrcode(), baseResult.getErrmsg());
        return ServerResponse.createByErrorCodeMsg(
                Integer.parseInt(baseResult.getErrcode()),
                errorMsg.getMsg() + baseResult.getErrmsg());
    }
}
