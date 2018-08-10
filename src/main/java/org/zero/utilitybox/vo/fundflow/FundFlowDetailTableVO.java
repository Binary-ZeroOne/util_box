package org.zero.utilitybox.vo.fundflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/19 11:39
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundFlowDetailTableVO {
    private FundFlowDetailHeadVO fundFlowDetailHead;
    private List<FundFlowDetailBodyVO> fundFlowDetailBody;
}
