package org.zero.utilitybox.vo.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 17:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillCountTableVO {
    private BillCountHeadVO billCountHead;
    private BillCountBodyVO billCountBody;
}
