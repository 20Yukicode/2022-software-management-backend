package com.campus.love.common.core.api;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),

    FAILED(500, "操作失败"),

    VALIDATE_FAILED(404, "参数检验失败"),

    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    FORBIDDEN(403, "没有相关权限"),

    FEIGN_FAILED(405,"远程调用失败");

//    TEST_FAILED(1000,"测试失败"),
//
//    NULL(123,"参数为空");

    private final long code;

    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
