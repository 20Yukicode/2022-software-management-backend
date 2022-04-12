package com.campus.love.common.core.util;

import com.campus.love.common.core.api.IErrorCode;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.exception.ApiException;

import java.lang.reflect.Field;
import java.util.Optional;

public class AssertUtil {

    public static void failed(String message) {
        throw new ApiException(message);
    }

    public static void failed(IErrorCode error) {
        throw new ApiException(error);
    }

    public static void failed(long code, String message) {
        IErrorCode errorCode = ResultCode.FAILED;
        Class<ResultCode> resultCodeClass = ResultCode.class;
        try {
            Field code1 = resultCodeClass.getDeclaredField("code");
            code1.setAccessible(true);
            Field message1 = resultCodeClass.getDeclaredField("message");
            message1.setAccessible(true);

            code1.set(errorCode, code);
            message1.set(errorCode, message);

            failed(errorCode);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            //ignore
        }
    }

    public static void ifNull(Object obj, String message) {
        if(obj==null){
            Optional.ofNullable(message)
                    .ifPresentOrElse(
                            m -> AssertUtil.failed(ResultCode.VALIDATE_FAILED.getCode(), m),
                            () -> AssertUtil.failed(ResultCode.VALIDATE_FAILED)
                    );
        }
    }
    /**
     * 判断是否为空
     *
     * @param obj
     * @param message
     */
    private static void isNull(Object obj, String message) {
        Optional.ofNullable(obj)
                .ifPresentOrElse(
                        (o) -> AssertUtil.failed(message),
                        () -> Optional.ofNullable(message)
                                .ifPresentOrElse(
                                        m -> AssertUtil.failed(ResultCode.VALIDATE_FAILED.getCode(), m),
                                        () -> AssertUtil.failed(ResultCode.VALIDATE_FAILED)
                                )
                );
    }

    /**
     * 判断是否为空
     *
     * @param obj
     */
    public static void isNull(Object obj) {
        isNull(obj, null);
    }


    public static void isNull(String message){
        isNull(null,message);
    }


}
