package org.zero.utilitybox.vo.fundflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/19 11:40
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundFlowCountBodyVO {
    /**
     * 资金流水总笔数
     */
    private String totalFundFlow;

    /**
     * 收入笔数
     */
    private String totalIncome;

    /**
     * 收入金额
     */
    private String totalIncomeFee;

    /**
     * 支出笔数
     */
    private String totalPay;

    /**
     * 支出金额
     */
    private String totalPayFee;
}
