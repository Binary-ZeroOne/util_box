package org.zero.utilitybox.vo.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 18:03
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailHeadVO {
    /**
     * 交易时间
     */
    private String tradeTimeStr;

    /**
     * 公众账号ID
     */
    private String appIdStr;

    /**
     * 商户号
     */
    private String mchIdStr;

    /**
     * 子商户号
     */
    private String subMchIdStr;

    /**
     * 设备号
     */
    private String deviceInfoStr;

    /**
     * 微信订单号
     */
    private String transactionIdStr;

    /**
     * 商户订单号
     */
    private String outTradeNoStr;

    /**
     * 用户标识
     */
    private String openIdStr;

    /**
     * 交易类型
     */
    private String tradeTypeStr;

    /**
     * 交易状态
     */
    private String tradeStateStr;

    /**
     * 付款银行
     */
    private String bankTypeStr;

    /**
     * 货币种类
     */
    private String feeTypeStr;

    /**
     * 总金额
     */
    private String totalFeeStr;

    /**
     * 企业红包金额
     */
    private String couponFeeStr;

    /**
     * 微信退款单号
     */
    private String refundIdStr;

    /**
     * 商户退款单号
     */
    private String outRefundNoStr;

    /**
     * 退款金额
     */
    private String settlementRefundFeeStr;

    /**
     * 企业红包退款金额
     */
    private String couponRefundFeeStr;

    /**
     * 退款类型
     */
    private String refundChannelStr;

    /**
     * 退款状态
     */
    private String refundStateStr;

    /**
     * 商品名称
     */
    private String bodyStr;

    /**
     * 商户数据包
     */
    private String attachStr;

    /**
     * 手续费
     */
    private String poundageStr;

    /**
     * 费率
     */
    private String poundageRateStr;
}
