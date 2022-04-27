package com.campus.love.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class HttpUtil {

    static {

        log.info("qidonghttp");
    }
    public static ServletRequestAttributes getAttributes() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes;
    }


    public static HttpServletRequest currentRequest() {
        return getAttributes().getRequest();
    }

    public static void setInheritable() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(servletRequestAttributes, true);
    }

    public static HttpServletResponse currentResponse() {
        return getAttributes().getResponse();
    }
}
