package com.example.springboot.utile;

public enum ResultCode {
    //成功
    SUCCESS(200),
    ISNODELETE(201),
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    FORBIDDEN(403), //禁止访问
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
