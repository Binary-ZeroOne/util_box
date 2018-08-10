package org.zero.utilitybox.vo.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 17:55
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailTableVO {
    private BillDetailHeadVO billDetailHead;
    private List<BillDetailBodyVO> billDetailBody;
}
