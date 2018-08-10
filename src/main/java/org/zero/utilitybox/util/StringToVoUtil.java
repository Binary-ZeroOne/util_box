package org.zero.utilitybox.util;

import org.zero.utilitybox.common.ServerResponse;
import org.zero.utilitybox.vo.bill.*;
import org.zero.utilitybox.vo.fundflow.*;
import weixin.popular.bean.paymch.base.BillResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/7/19 10:40
 * @Description: 微信支付-对账单、资金账单等String类型数据转vo工具类
 */
public class StringToVoUtil {

    /**
     * bom头，非法字符标识
     */
    private static final String BOM_HEAD = "\ufeff";
    private static final String BILL_BORDER = "总交易单数";
    private static final String FUND_BORDER = "资金流水总笔数";
    private static final String LINE_BREAK = "\r\n";
    private static final String COMMA = ",";
    private static final String BILL_MARK = "`";
    private static final String EMPTY_STR = "";

    /**
     * 组装BillTableVO实例
     *
     * @param billResult billResult
     * @return ServerResponse<BillTableVO>
     */
    public static ServerResponse<BillTableVO> getBillTableVO(BillResult billResult) {
        String tableData = billResult.getData();
        int borderIndex = tableData.indexOf(BILL_BORDER);

        // 分离出两张表格
        String billDetailStr = tableData.substring(0, borderIndex);
        String billCountStr = tableData.substring(borderIndex);

        // 分别组装两张表的数据
        BillCountTableVO countTableVO = getBillCountTableVO(billCountStr);
        BillDetailTableVO detailTableVO = getBillDetailTableVO(billDetailStr);

        BillTableVO billTableVO = new BillTableVO();
        billTableVO.setBillCountTable(countTableVO);
        billTableVO.setBillDetailTable(detailTableVO);

        return ServerResponse.createBySuccessData(billTableVO);
    }

    /**
     * 组装 FundFlowTableVO 实例
     *
     * @param fundFlowResult fundFlowResult
     * @return ServerResponse<FundFlowTableVO>
     */
    public static ServerResponse<FundFlowTableVO> getFundFlowTableVO(BillResult fundFlowResult) {
        String tableData = fundFlowResult.getData();
        int borderIndex = tableData.indexOf(FUND_BORDER);

        // 分离出两张表格
        String fundFlowDetailStr = tableData.substring(0, borderIndex);
        String fundFlowCountStr = tableData.substring(borderIndex);

        // 分别组装两张表的数据
        FundFlowCountTableVO countTableVO = getFundFlowCountTableVO(fundFlowCountStr);
        FundFlowDetailTableVO detailTableVO = getFundFlowDetailTableVO(fundFlowDetailStr);

        FundFlowTableVO fundFlowTableVO = new FundFlowTableVO();
        fundFlowTableVO.setFundFlowCountTable(countTableVO);
        fundFlowTableVO.setFundFlowDetailTable(detailTableVO);

        return ServerResponse.createBySuccessData(fundFlowTableVO);
    }

    /**
     * 组装 FundFlowDetailTableVO 实例
     *
     * @param fundFlowDetailStr fundFlowDetailStr
     * @return FundFlowDetailTableVO
     */
    private static FundFlowDetailTableVO getFundFlowDetailTableVO(String fundFlowDetailStr) {
        int firstIndex = fundFlowDetailStr.indexOf(LINE_BREAK);
        String head = fundFlowDetailStr.substring(0, firstIndex);

        String body = fundFlowDetailStr.substring(firstIndex + 2);
        body = body.replaceAll(BILL_MARK, EMPTY_STR);

        FundFlowDetailHeadVO headVO = getFundFlowDetailHeadVO(head);
        List<FundFlowDetailBodyVO> bodyVOList = getFundFlowDetailBodyVO(body);

        FundFlowDetailTableVO fundFlowDetailTableVO = new FundFlowDetailTableVO();
        fundFlowDetailTableVO.setFundFlowDetailHead(headVO);
        fundFlowDetailTableVO.setFundFlowDetailBody(bodyVOList);

        return fundFlowDetailTableVO;
    }

    /**
     * 组装 FundFlowDetailBodyVO 实例列表
     *
     * @param body body
     * @return List<FundFlowDetailBodyVO>
     */
    private static List<FundFlowDetailBodyVO> getFundFlowDetailBodyVO(String body) {
        List<FundFlowDetailBodyVO> bodyVOList = new ArrayList<>();
        String[] bodyData = body.split(LINE_BREAK);
        for (String bodyDatum : bodyData) {
            String[] bodyValues = bodyDatum.split(COMMA);
            FundFlowDetailBodyVO bodyVO = new FundFlowDetailBodyVO();
            bodyVO.setKeepAccountTime(bodyValues[0]);
            bodyVO.setWxBusinessId(bodyValues[1]);
            bodyVO.setFundFlowId(bodyValues[2]);
            bodyVO.setBusinessName(bodyValues[3]);
            bodyVO.setBusinessType(bodyValues[4]);
            bodyVO.setTranType(bodyValues[5]);
            bodyVO.setAmount(bodyValues[6]);
            bodyVO.setAccountBalance(bodyValues[7]);
            bodyVO.setApplicant(bodyValues[8]);
            bodyVO.setRemarks(bodyValues[9]);
            bodyVO.setVoucherId(bodyValues[10]);

            bodyVOList.add(bodyVO);
        }

        return bodyVOList;
    }

    /**
     * 组装 FundFlowDetailHeadVO 实例
     *
     * @param head head
     * @return FundFlowDetailHeadVO
     */
    private static FundFlowDetailHeadVO getFundFlowDetailHeadVO(String head) {
        // 去除bom头
        if (head.startsWith(BOM_HEAD)) {
            head = head.replace(BOM_HEAD, EMPTY_STR);
        }

        String[] headData = head.split(COMMA);
        FundFlowDetailHeadVO headVO = new FundFlowDetailHeadVO();
        headVO.setKeepAccountTimeStr(headData[0]);
        headVO.setWxBusinessIdStr(headData[1]);
        headVO.setFundFlowIdStr(headData[2]);
        headVO.setBusinessNameStr(headData[3]);
        headVO.setBusinessTypeStr(headData[4]);
        headVO.setTranTypeStr(headData[5]);
        headVO.setAmountStr(headData[6]);
        headVO.setAccountBalanceStr(headData[7]);
        headVO.setApplicantStr(headData[8]);
        headVO.setRemarksStr(headData[9]);
        headVO.setVoucherIdStr(headData[10]);

        return headVO;
    }

    /**
     * 组装 FundFlowCountTableVO 实例
     *
     * @param fundFlowCountStr fundFlowCountStr
     * @return FundFlowCountTableVO
     */
    private static FundFlowCountTableVO getFundFlowCountTableVO(String fundFlowCountStr) {
        fundFlowCountStr = fundFlowCountStr.replaceAll(LINE_BREAK, EMPTY_STR);
        int firstIndex = fundFlowCountStr.indexOf(BILL_MARK);
        String head = fundFlowCountStr.substring(0, firstIndex);

        String body = fundFlowCountStr.substring(firstIndex);
        body = body.replaceAll(BILL_MARK, EMPTY_STR);

        FundFlowCountHeadVO fundFlowCountHeadVO = getFundFlowCountHeadVO(head);
        FundFlowCountBodyVO fundFlowCountBodyVO = getFundFlowCountBodyVO(body);

        FundFlowCountTableVO fundFlowCountTableVO = new FundFlowCountTableVO();
        fundFlowCountTableVO.setFundFlowCountHead(fundFlowCountHeadVO);
        fundFlowCountTableVO.setFundFlowCountBody(fundFlowCountBodyVO);

        return fundFlowCountTableVO;
    }

    /**
     * 组装 FundFlowCountBodyVO 实例
     *
     * @param body body
     * @return FundFlowCountBodyVO
     */
    private static FundFlowCountBodyVO getFundFlowCountBodyVO(String body) {
        FundFlowCountBodyVO fundFlowCountBodyVO = new FundFlowCountBodyVO();
        String[] bodyData = body.split(COMMA);

        fundFlowCountBodyVO.setTotalFundFlow(bodyData[0]);
        fundFlowCountBodyVO.setTotalIncome(bodyData[1]);
        fundFlowCountBodyVO.setTotalIncomeFee(bodyData[2]);
        fundFlowCountBodyVO.setTotalPay(bodyData[3]);
        fundFlowCountBodyVO.setTotalPayFee(bodyData[4]);

        return fundFlowCountBodyVO;
    }

    /**
     * 组装 FundFlowCountHeadVO 实例
     *
     * @param head head
     * @return FundFlowCountHeadVO
     */
    private static FundFlowCountHeadVO getFundFlowCountHeadVO(String head) {
        FundFlowCountHeadVO fundFlowCountHeadVO = new FundFlowCountHeadVO();
        String[] headData = head.split(COMMA);

        fundFlowCountHeadVO.setTotalFundFlowStr(headData[0]);
        fundFlowCountHeadVO.setTotalIncomeStr(headData[1]);
        fundFlowCountHeadVO.setTotalIncomeFeeStr(headData[2]);
        fundFlowCountHeadVO.setTotalPayStr(headData[3]);
        fundFlowCountHeadVO.setTotalPayFeeStr(headData[4]);

        return fundFlowCountHeadVO;
    }

    /**
     * 组装 BillDetailTableVO 实例
     *
     * @param billDetailStr billDetailStr
     * @return BillDetailTableVO
     */
    private static BillDetailTableVO getBillDetailTableVO(String billDetailStr) {
        int firstIndex = billDetailStr.indexOf(LINE_BREAK);
        String head = billDetailStr.substring(0, firstIndex);

        String body = billDetailStr.substring(firstIndex + 2);
        body = body.replaceAll(BILL_MARK, EMPTY_STR);

        BillDetailHeadVO headVO = getBillDetailHeadVO(head);
        List<BillDetailBodyVO> bodyVOList = getBillDetailBodyVO(body);

        BillDetailTableVO billDetailTableVO = new BillDetailTableVO();
        billDetailTableVO.setBillDetailHead(headVO);
        billDetailTableVO.setBillDetailBody(bodyVOList);

        return billDetailTableVO;
    }

    /**
     * 组装 BillDetailBodyVO 实例列表
     *
     * @param body body
     * @return List<BillDetailBodyVO>
     */
    private static List<BillDetailBodyVO> getBillDetailBodyVO(String body) {
        List<BillDetailBodyVO> bodyVOList = new ArrayList<>();
        String[] bodyData = body.split(LINE_BREAK);
        for (String bodyDatum : bodyData) {
            String[] bodyValues = bodyDatum.split(COMMA);
            BillDetailBodyVO bodyVO = new BillDetailBodyVO();
            bodyVO.setTradeTime(bodyValues[0]);
            bodyVO.setAppId(bodyValues[1]);
            bodyVO.setMchId(bodyValues[2]);
            bodyVO.setSubMchId(bodyValues[3]);
            bodyVO.setDeviceInfo(bodyValues[4]);
            bodyVO.setTransactionId(bodyValues[5]);
            bodyVO.setOutTradeNo(bodyValues[6]);
            bodyVO.setOpenId(bodyValues[7]);
            bodyVO.setTradeType(bodyValues[8]);
            bodyVO.setTradeState(bodyValues[9]);
            bodyVO.setBankType(bodyValues[10]);
            bodyVO.setFeeType(bodyValues[11]);
            bodyVO.setTotalFee(bodyValues[12]);
            bodyVO.setCouponFee(bodyValues[13]);
            bodyVO.setRefundId(bodyValues[14]);
            bodyVO.setOutRefundNo(bodyValues[15]);
            bodyVO.setSettlementRefundFee(bodyValues[16]);
            bodyVO.setCouponRefundFee(bodyValues[17]);
            bodyVO.setRefundChannel(bodyValues[18]);
            bodyVO.setRefundState(bodyValues[19]);
            bodyVO.setBody(bodyValues[20]);
            bodyVO.setAttach(bodyValues[21]);
            bodyVO.setPoundage(bodyValues[22]);
            bodyVO.setPoundageRate(bodyValues[23]);

            bodyVOList.add(bodyVO);
        }

        return bodyVOList;
    }

    /**
     * 组装 BillDetailHeadVO 实例
     *
     * @param head head
     * @return BillDetailHeadVO
     */
    private static BillDetailHeadVO getBillDetailHeadVO(String head) {
        // 去除bom头
        if (head.startsWith(BOM_HEAD)) {
            head = head.replace(BOM_HEAD, EMPTY_STR);
        }

        String[] headData = head.split(COMMA);
        BillDetailHeadVO headVO = new BillDetailHeadVO();
        headVO.setTradeTimeStr(headData[0]);
        headVO.setAppIdStr(headData[1]);
        headVO.setMchIdStr(headData[2]);
        headVO.setSubMchIdStr(headData[3]);
        headVO.setDeviceInfoStr(headData[4]);
        headVO.setTransactionIdStr(headData[5]);
        headVO.setOutTradeNoStr(headData[6]);
        headVO.setOpenIdStr(headData[7]);
        headVO.setTradeTypeStr(headData[8]);
        headVO.setTradeStateStr(headData[9]);
        headVO.setBankTypeStr(headData[10]);
        headVO.setFeeTypeStr(headData[11]);
        headVO.setTotalFeeStr(headData[12]);
        headVO.setCouponFeeStr(headData[13]);
        headVO.setRefundIdStr(headData[14]);
        headVO.setOutRefundNoStr(headData[15]);
        headVO.setSettlementRefundFeeStr(headData[16]);
        headVO.setCouponRefundFeeStr(headData[17]);
        headVO.setRefundChannelStr(headData[18]);
        headVO.setRefundStateStr(headData[19]);
        headVO.setBodyStr(headData[20]);
        headVO.setAttachStr(headData[21]);
        headVO.setPoundageStr(headData[22]);
        headVO.setPoundageRateStr(headData[23]);

        return headVO;
    }

    /**
     * 组装 BillCountTableVO 实例
     *
     * @param billCountStr billCountStr
     * @return BillCountTableVO
     */
    private static BillCountTableVO getBillCountTableVO(String billCountStr) {
        billCountStr = billCountStr.replaceAll(LINE_BREAK, EMPTY_STR);
        int firstIndex = billCountStr.indexOf(BILL_MARK);
        String head = billCountStr.substring(0, firstIndex);

        String body = billCountStr.substring(firstIndex);
        body = body.replaceAll(BILL_MARK, EMPTY_STR);


        BillCountHeadVO billCountHeadVO = getBillCountHeadVO(head);
        BillCountBodyVO billCountBodyVO = getBillCountBodyVO(body);

        BillCountTableVO billCountTableVO = new BillCountTableVO();
        billCountTableVO.setBillCountHead(billCountHeadVO);
        billCountTableVO.setBillCountBody(billCountBodyVO);

        return billCountTableVO;
    }

    /**
     * 组装 BillCountHeadVO 实例
     *
     * @param head head
     * @return BillCountHeadVO
     */
    private static BillCountHeadVO getBillCountHeadVO(String head) {
        BillCountHeadVO billCountHeadVO = new BillCountHeadVO();
        String[] headData = head.split(COMMA);

        billCountHeadVO.setTotalRecordStr(headData[0]);
        billCountHeadVO.setTotalFeeStr(headData[1]);
        billCountHeadVO.setTotalRefundFeeStr(headData[2]);
        billCountHeadVO.setTotalCouponFeeStr(headData[3]);
        billCountHeadVO.setTotalPoundageFeeStr(headData[4]);

        return billCountHeadVO;
    }

    /**
     * 组装 BillCountBodyVO 实例
     *
     * @param body body
     * @return BillCountBodyVO
     */
    private static BillCountBodyVO getBillCountBodyVO(String body) {
        BillCountBodyVO billCountBodyVO = new BillCountBodyVO();
        String[] bodyData = body.split(COMMA);

        billCountBodyVO.setTotalRecord(bodyData[0]);
        billCountBodyVO.setTotalFee(bodyData[1]);
        billCountBodyVO.setTotalRefundFee(bodyData[2]);
        billCountBodyVO.setTotalCouponFee(bodyData[3]);
        billCountBodyVO.setTotalPoundageFee(bodyData[4]);

        return billCountBodyVO;
    }
}
