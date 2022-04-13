package com.campus.love.common.core.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageModel<T> implements Serializable {

    @ApiModelProperty("状态码")
    private long code;

    @ApiModelProperty("状态信息")
    private String message;

    @ApiModelProperty("data")
    private T data;

    private MessageModel(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static<T> MessageModel<T> result(IErrorCode result, T data) {
        return new MessageModel<>(result.getCode(), result.getMessage(), data);
    }

    public static<T> MessageModel<T> result(long code, String message, T data) {
        return new MessageModel<>(code, message, data);
    }

    public static<T> MessageModel<T> success() {
        return result(ResultCode.SUCCESS, null);
    }

    public static<T> MessageModel<T> success(T data) {
        return result(ResultCode.SUCCESS, data);
    }

    public static<T> MessageModel<T> success(String message, T data) {
        return result(ResultCode.SUCCESS.getCode(), message, data);
    }

    public static<T> MessageModel<T> failed() {
        return result(ResultCode.FAILED, null);
    }

    public static<T> MessageModel<T> failed(IErrorCode error) {
        return result(error, null);
    }

    public static<T> MessageModel<T> failed(String message) {
        return result(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 没有权限
     *
     * @return
     */
    public static<T> MessageModel<T> forbidden(String message) {
        return result(ResultCode.FORBIDDEN.getCode(),message, null);
    }

    public static<T> MessageModel<T> forbidden() {
        return result(ResultCode.FORBIDDEN, null);
    }

    /**
     * 参数校验失败
     *
     * @return
     */
    public static<T> MessageModel<T> validateFailed(String message) {
        return result(ResultCode.VALIDATE_FAILED.getCode(),message,null);
    }

    public static<T> MessageModel<T> validateFailed() {
        return result(ResultCode.VALIDATE_FAILED,null);
    }

    /**
     * 暂未登录
     *
     * @return
     */
    public static<T> MessageModel<T> unauthorized(String message) {
        return result(ResultCode.UNAUTHORIZED.getCode(), message,null);
    }

    public static<T> MessageModel<T> unauthorized() {
        return result(ResultCode.UNAUTHORIZED,null);
    }


    /**
     * 远程调用服务失败
     * @param message
     * @param <T>
     * @return
     */
    public static<T> MessageModel<T> feignFailed(String message){
        return result(ResultCode.FEIGN_FAILED.getCode(),message,null);
    }

    public static <T> MessageModel<T> feignFailed(){
        return result(ResultCode.FEIGN_FAILED,null);
    }

}
