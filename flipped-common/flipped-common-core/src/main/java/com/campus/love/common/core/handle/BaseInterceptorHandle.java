package com.campus.love.common.core.handle;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

public abstract class BaseInterceptorHandle implements WebMvcConfigurer {

    @Resource
    private GatewayInterceptor gatewayInterceptor;
    /**
     * spring升级后，通配符不再是**
     * 所以白名单要修改
     * @return
     */
    public abstract List<String> whiteList();

    public void addInterceptors(InterceptorRegistry registry) {
        List<String> whiteList = whiteList();
        registry.addInterceptor(gatewayInterceptor)
                //.addPathPatterns("/**")
                .excludePathPatterns(whiteList);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
