package com.campus.love.common.core.handle;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.campus.love.common.core.api.ResultCode;
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
        String secretKey = request.getHeader("gateway");
        if (StringUtils.isBlank(secretKey) || !secretKey.equals("gateway")) {
            setReturn(response, ResultCode.FORBIDDEN);
            return false;
        }
        return true;
    }

}
