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
public class BillCountBodyVO {
    /**
     * 总交易单数
     */
    private String totalRecord;

    /**
     * 总交易额
     */
    private String totalFee;

    /**
     * 总退款金额
     */
    private String totalRefundFee;

    /**
     * 总代金券或立减优惠退款金额
     */
    private String totalCouponFee;

    /**
     * 手续费总金额
     */
    private String totalPoundageFee;
}
