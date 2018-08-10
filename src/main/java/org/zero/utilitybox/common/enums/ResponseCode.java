package org.zero.utilitybox.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @ProjectName applicationBox
 * @Author: zeroJun
 * @Date: 2018/7/31 10:19
 * @Description:
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 正确响应
     */
    SUCCESS(0, "SUCCESS"),

    /**
     * 错误响应
     */
    ERROR(1, "ERROR"),

    /**
     * 参数错误
     */
    PAEAM_ERROR(2, "参数错误"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(3, "登录成功"),

    /**
     * 登录失败
     */
    LOGIN_ERROR(4, "登录失败"),

    /**
     * 服务器获取数据失败
     */
    OPERATION_RETRY(6, "服务器获取数据失败, 请重试"),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(7, "注册成功"),

    /**
     * 注册失败
     */
    REGISTER_ERROR(8, "注册失败"),

    /**
     * 用户已存在
     */
    USER_EXIST(9, "用户已存在"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(10, "用户不存在"),

    /**
     * 添加成功
     */
    ADD_SUCCESS(11, "添加成功"),

    /**
     * 添加失败
     */
    ADD_ERROR(12, "添加失败"),

    /**
     * 查询目标不存在
     */
    NOT_EXIST(13, "查询目标不存在"),

    /**
     * FAIL
     */
    FAIL(14,"FAIL"),

    /**
     * 服务器内部错误
     */
    UNKONW_ERROR(250, "抱歉, 服务器开小差去了"),
    ;

    private Integer code;
    private String msg;
}
