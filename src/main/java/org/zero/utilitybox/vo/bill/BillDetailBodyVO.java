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
public class BillDetailBodyVO {
    /**
     * 交易时间
     */
    private String tradeTime;

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 子商户号
     */
    private String subMchId;

    /**
     * 设备号
     */
    private String deviceInfo;

    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 用户标识
     */
    private String openId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 付款银行
     */
    private String bankType;

    /**
     * 货币种类
     */
    private String feeType;

    /**
     * 总金额
     */
    private String totalFee;

    /**
     * 企业红包金额
     */
    private String couponFee;

    /**
     * 微信退款单号
     */
    private String refundId;

    /**
     * 商户退款单号
     */
    private String outRefundNo;

    /**
     * 退款金额
     */
    private String settlementRefundFee;

    /**
     * 企业红包退款金额
     */
    private String couponRefundFee;

    /**
     * 退款类型
     */
    private String refundChannel;

    /**
     * 退款状态
     */
    private String refundState;

    /**
     * 商品名称
     */
    private String body;

    /**
     * 商户数据包
     */
    private String attach;

    /**
     * 手续费
     */
    private String poundage;

    /**
     * 费率
     */
    private String poundageRate;
}
