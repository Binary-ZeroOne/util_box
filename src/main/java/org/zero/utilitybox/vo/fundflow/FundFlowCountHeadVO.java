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
public class FundFlowCountHeadVO {

    /**
     * 资金流水总笔数
     */
    private String totalFundFlowStr;

    /**
     * 收入笔数
     */
    private String totalIncomeStr;

    /**
     * 收入金额
     */
    private String totalIncomeFeeStr;

    /**
     * 支出笔数
     */
    private String totalPayStr;

    /**
     * 支出金额
     */
    private String totalPayFeeStr;
}
