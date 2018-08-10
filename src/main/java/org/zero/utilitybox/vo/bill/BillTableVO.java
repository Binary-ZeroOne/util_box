package org.zero.utilitybox.vo.bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/18 15:53
 * @Description: 对账单表格视图数据对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillTableVO {
    private BillDetailTableVO billDetailTable;
    private BillCountTableVO billCountTable;
}
