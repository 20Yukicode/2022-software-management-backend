package com.campus.love.common.core.handle;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

public abstract class BaseInterceptorHandle implements WebMvcConfigurer {

    @Resource
    private GatewayInterceptor gatewayInterceptor;
    /**
     * spring升级后，通配符不再是**
     * 所以白名单要修改
     * @return
     */

    public abstract void moduleInterceptor(InterceptorRegistry registry);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(gatewayInterceptor);
                //.addPathPatterns("/**")

        moduleInterceptor(registry);

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
