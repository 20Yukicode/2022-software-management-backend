package com.campus.love.common.feign.interceptor;

import com.campus.love.common.core.constant.HeaderConstant;
import com.campus.love.common.core.util.HttpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 用于Feign传递请求头的拦截器
 * 只有在模块间调用才会触发这个东西
 * 直接通过url调用是不行的
 */
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HeaderConstant.FEIGN_HEADER_VALUE, HeaderConstant.FEIGN_HEADER_VALUE);

        RequestContextHolder
                .setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        HttpServletRequest request = HttpUtil.currentRequest();

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                requestTemplate.header(name, value);
            }
        }

    }


}