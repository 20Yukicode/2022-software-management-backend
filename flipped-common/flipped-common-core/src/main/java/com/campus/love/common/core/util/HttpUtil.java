package com.campus.love.common.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {

    public static ServletRequestAttributes getAttributes() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes;
    }


    public static HttpServletRequest currentRequest() {
        return getAttributes().getRequest();
    }

    public static HttpServletResponse currentResponse() {
        return getAttributes().getResponse();
    }
}
