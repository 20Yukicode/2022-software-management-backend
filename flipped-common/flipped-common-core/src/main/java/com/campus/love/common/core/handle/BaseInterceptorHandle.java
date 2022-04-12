package com.campus.love.common.core.handle;

import com.campus.love.common.core.domain.EnableProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

public abstract class BaseInterceptorHandle implements WebMvcConfigurer {

    @Resource
    private GatewayInterceptor gatewayInterceptor;

    @Resource
    private FeignInterceptor feignInterceptor;

    public abstract EnableProperties enableProperties();

    public void moduleInterceptor(InterceptorRegistry registry){}

    /**
     * spring升级后，通配符不再是**
     * 所以白名单要修改
     * @return
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(enableProperties()!=null) {

            if (enableProperties().getGateway()) {
                registry.addInterceptor(gatewayInterceptor)
                        .excludePathPatterns("/feign/*");
            }
            //.addPathPatterns("/**")
            if (enableProperties().getFeign()) {
                registry.addInterceptor(feignInterceptor)
                        .addPathPatterns("/feign/*");
            }
            if (enableProperties().getModule()) {
                moduleInterceptor(registry);
            }

            WebMvcConfigurer.super.addInterceptors(registry);
        }
    }
}
