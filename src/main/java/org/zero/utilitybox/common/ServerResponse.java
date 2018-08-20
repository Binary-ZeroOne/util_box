package org.zero.utilitybox.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.zero.utilitybox.common.enums.ResponseEnum;

import java.io.Serializable;

/**
 * @ProjectName dabo-platform
 * @Author: zeroJun
 * @Date: 2018/6/26 10:06
 * @Description: 封装统一的数据返回格式
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private static final long serialVersionUID = 2568635293459737592L;

    private int code;
    private String msg;
    private T data;

    private ServerResponse(int code) {
        this.code = code;
    }

    private ServerResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseEnum.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> createByCodeSuccess() {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccessMsg() {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(ResponseEnum successEnum) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), successEnum.getMsg());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccessMsg(ResponseEnum successEnum, T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), successEnum.getMsg(), data);
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg, T data) {
        return new ServerResponse<T>(ResponseEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByCodeError() {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode());
    }

    public static <T> ServerResponse<T> createByErrorMsg() {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorMsg(ResponseEnum errorEnum) {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), errorEnum.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorMsg(String errorMsg) {
        return new ServerResponse<T>(ResponseEnum.ERROR.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMsg(int code, String errorMsg) {
        return new ServerResponse<T>(code, errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMsg(int code, ResponseEnum errorEnum) {
        return new ServerResponse<T>(code, errorEnum.getMsg());
    }
}
