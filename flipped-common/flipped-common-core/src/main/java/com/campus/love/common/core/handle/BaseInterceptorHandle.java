package com.campus.love.common.core.handle;

import com.campus.love.common.core.config.AuthenticationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Slf4j
public abstract class BaseInterceptorHandle implements WebMvcConfigurer {

    @Resource
    private GatewayInterceptor gatewayInterceptor;

    @Resource
    private FeignInterceptor feignInterceptor;

    @Resource
    private AuthenticationConfig authenticationConfig;

    public abstract void moduleInterceptor(InterceptorRegistry registry);

    /**
     * spring升级后，通配符不再是**
     * 所以白名单要修改
     * @return
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authenticationConfig.isMustGateway()) {
            registry.addInterceptor(gatewayInterceptor)
                    .excludePathPatterns("/feign/*");
        }
        if (authenticationConfig.isMustFeign()) {
            registry.addInterceptor(feignInterceptor)
                    .addPathPatterns("/feign/*");
        }
        moduleInterceptor(registry);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        if(enableAuthentication()!=null) {
//
//            if (enableAuthentication().getGateway()) {
//                registry.addInterceptor(gatewayInterceptor)
//                        .excludePathPatterns("/feign/*");
//            }
//            //.addPathPatterns("/**")
//            if (enableAuthentication().getFeign()) {
//                registry.addInterceptor(feignInterceptor)
//                        .addPathPatterns("/feign/*");
//            }
//            if (enableAuthentication().getModule()) {
//                moduleInterceptor(registry);
//            }
//
//            WebMvcConfigurer.super.addInterceptors(registry);
//        }
//    }

}
