package com.example.springboot.exception;

import com.example.springboot.enume.ErrorMessage;
import lombok.Data;
import lombok.Getter;


/**
 * 自定义全局异常处理类
 */
@Data
public class CustomExceptionHandler extends RuntimeException {

    private Integer code;
    private String msg;
    public CustomExceptionHandler() {
        super();
    }

    public CustomExceptionHandler(ErrorMessage errorInfoInterface) {
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public CustomExceptionHandler(ErrorMessage errorInfoInterface, Throwable cause) {
        this.code = errorInfoInterface.getCode();
        this.msg = errorInfoInterface.getMsg();
    }

    public CustomExceptionHandler(String errorMsg) {
        super(errorMsg);
        this.msg = errorMsg;
    }

    public CustomExceptionHandler(Integer errorCode, String errorMsg) {
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public CustomExceptionHandler(Integer errorCode, String errorMsg, Throwable cause) {
        this.code = errorCode;
        this.msg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
