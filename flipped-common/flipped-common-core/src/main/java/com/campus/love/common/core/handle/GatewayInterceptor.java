package com.campus.love.common.core.handle;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.constant.HeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.campus.love.common.core.util.InterceptorUtil.setReturn;


@Component
@Slf4j
public class GatewayInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secretKey = request.getHeader(HeaderConstant.GATEWAY_HEADER_KEY);
        if (StringUtils.isBlank(secretKey) || !secretKey.equals(HeaderConstant.GATEWAY_HEADER_VALUE)) {
            setReturn(response, ResultCode.FORBIDDEN);
            log.info("gateway拦截了");
            return false;
        }
        return true;
    }

}
