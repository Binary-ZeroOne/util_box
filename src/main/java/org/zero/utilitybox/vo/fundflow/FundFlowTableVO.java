package org.zero.utilitybox.vo.fundflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/19 11:37
 * @Description: 资金账单视图数据对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundFlowTableVO {
    private FundFlowCountTableVO fundFlowCountTable;
    private FundFlowDetailTableVO fundFlowDetailTable;
}
