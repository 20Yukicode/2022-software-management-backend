package com.campus.love.common.core.util;

import com.campus.love.common.core.api.IErrorCode;
import com.campus.love.common.core.exception.ApiException;

public class AssertUtil {

    public static void failed(String message) {
        throw new ApiException(message);
    }

    public static void failed(IErrorCode error) {
        throw new ApiException(error);
    }
}
