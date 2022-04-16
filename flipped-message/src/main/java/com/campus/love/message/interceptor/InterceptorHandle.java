package com.campus.love.message.interceptor;

import com.campus.love.common.core.handle.BaseInterceptorHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class InterceptorHandle extends BaseInterceptorHandle {

    @Override
    public void moduleInterceptor(InterceptorRegistry registry) {

    }
}
