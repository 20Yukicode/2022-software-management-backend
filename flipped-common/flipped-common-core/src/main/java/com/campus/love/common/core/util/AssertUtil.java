package com.campus.love.common.core.util;

import com.campus.love.common.core.api.IErrorCode;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.exception.ApiException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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

    private static void nullFunction(Object obj, String message, ResultCode code) {
        if (obj == null) {
            Optional.ofNullable(message)
                    .ifPresentOrElse((m) -> AssertUtil.failed(code.getCode(), m),
                            () -> AssertUtil.failed(code)
                    );
        }
    }

    private static void nullFunction(Collection<?> collection, String message, ResultCode code) {
        if (collection == null || collection.size() == 0) {
            Optional.ofNullable(message)
                    .ifPresentOrElse((m) -> AssertUtil.failed(code.getCode(), m),
                            () -> AssertUtil.failed(code)
                    );
        }
    }

    private static void nullFunction(Supplier<Boolean> supplier, String message, ResultCode code) {
        if (supplier.get()) {
            Optional.ofNullable(message)
                    .ifPresentOrElse((m) -> AssertUtil.failed(code.getCode(), m),
                            () -> AssertUtil.failed(code)
                    );
        }
    }

    /**
     * 用来controller层的参数校验
     *
     * @param obj
     * @param message
     */
    public static void validateNull(Object obj, String message) {
        nullFunction(obj, message, ResultCode.VALIDATE_FAILED);
    }

    /**
     * 用于业务层的判空检验
     *
     * @param obj
     * @param message
     */
    public static void ifNull(Object obj, String message) {
        nullFunction(obj, message, ResultCode.FAILED);
    }

    /**
     * 用于业务层的判空校验
     *
     * @param obj
     * @param message
     */
    public static void ifNull(Collection<?> obj, String message) {
        nullFunction(obj, message, ResultCode.FAILED);
    }

    /**
     * 用于业务层的判空校验
     *
     * @param supplier
     * @param message
     */
    public static void ifNull(Supplier<Boolean> supplier, String message) {
        nullFunction(supplier, message, ResultCode.FAILED);
    }

}
