package org.zero.utilitybox.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.zero.utilitybox.common.enums.ResponseCode;

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
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> createByCodeSuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccessData(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int code, String errorMsg) {
        return new ServerResponse<T>(code, errorMsg);
    }
}
