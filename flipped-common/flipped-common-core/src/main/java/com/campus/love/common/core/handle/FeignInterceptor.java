package com.campus.love.common.core.handle;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.campus.love.common.core.constant.HeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.campus.love.common.core.util.InterceptorUtil.setReturn;

@Component
@Slf4j
public class FeignInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String feign = request.getHeader(HeaderConstant.FEIGN_HEADER_KEY);
        if (StringUtils.isBlank(feign) || !HeaderConstant.FEIGN_HEADER_VALUE.equals(feign)) {
            setReturn(response);
            log.info("被feign拦截了");
            return false;
        }
        return true;
    }
}
