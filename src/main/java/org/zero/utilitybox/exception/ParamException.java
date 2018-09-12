package org.zero.utilitybox.exception;

/**
 * @program: permission-system
 * @description: 参数异常
 * @author: 01
 * @create: 2018-09-12 21:24
 **/
public class ParamException extends RuntimeException {
    public ParamException(String exception) {
        super(exception);
    }
}
