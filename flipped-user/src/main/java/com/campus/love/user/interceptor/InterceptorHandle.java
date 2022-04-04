package com.campus.love.user.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.handle.BaseInterceptorHandle;
import com.campus.love.common.core.util.InterceptorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
@RefreshScope
public class InterceptorHandle extends BaseInterceptorHandle {

    @Value("${module.isIntercept}")
    private boolean isIntercept;

    @Override
    public void moduleInterceptor(InterceptorRegistry registry) {
        if(isIntercept) {
            HandlerInterceptor interceptor = new ModuleInterceptor();
            registry.addInterceptor(interceptor)
                    .excludePathPatterns("/login");
        }
    }

    @Component
    private static class ModuleInterceptor implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String auth = request.getHeader("Auth");
            log.info(request.getRequestURL().toString());
            Object id = StpUtil.getLoginIdByToken(auth);
            log.info("userId:" + id);
            if (id != null) {
                return true;
            } else {
                InterceptorUtil.setReturn(response, ResultCode.UNAUTHORIZED);
                return false;
            }
        }
    }
}
