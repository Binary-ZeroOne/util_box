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
public class FundFlowCountTableVO {
    private FundFlowCountHeadVO fundFlowCountHead;
    private FundFlowCountBodyVO fundFlowCountBody;
}
