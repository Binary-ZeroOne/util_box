package org.zero.utilitybox.vo.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 18:04
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCountHeadVO {
    /**
     * 总交易单数
     */
    private String totalRecordStr;

    /**
     * 总交易额
     */
    private String totalFeeStr;

    /**
     * 总退款金额
     */
    private String totalRefundFeeStr;

    /**
     * 总代金券或立减优惠退款金额
     */
    private String totalCouponFeeStr;

    /**
     * 手续费总金额
     */
    private String totalPoundageFeeStr;
}
