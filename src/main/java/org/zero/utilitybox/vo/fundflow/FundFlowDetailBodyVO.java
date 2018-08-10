package org.zero.utilitybox.vo.fundflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/19 11:41
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundFlowDetailBodyVO {

    /**
     * 记账时间
     */
    private String keepAccountTime;

    /**
     * 微信支付业务单号
     */
    private String wxBusinessId;

    /**
     * 资金流水单号
     */
    private String fundFlowId;

    /**
     * 业务名称
     */
    private String businessName;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 收支类型
     */
    private String tranType;

    /**
     * 收支金额（元）
     */
    private String amount;

    /**
     * 账户结余（元）
     */
    private String accountBalance;

    /**
     * 资金变更提交申请人
     */
    private String applicant;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 业务凭证号
     */
    private String voucherId;
}
