package com.campus.love.user.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.handle.BaseInterceptorHandle;
import com.campus.love.common.core.util.InterceptorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Configuration
@Slf4j
public class InterceptorHandle extends BaseInterceptorHandle {

    @Override
    public void moduleInterceptor(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new ModuleInterceptor();
        registry.addInterceptor(interceptor)
                .excludePathPatterns("/login", "/all");
    }

    @Component
    private static class ModuleInterceptor implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            try {
                StpUtil.checkLogin();
                return true;
            } catch (Exception e) {
                InterceptorUtil.setReturn(response, ResultCode.UNAUTHORIZED);
                return false;
            }
        }
    }
}
