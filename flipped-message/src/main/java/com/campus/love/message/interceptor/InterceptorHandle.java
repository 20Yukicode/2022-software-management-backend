package com.campus.love.message.interceptor;

import com.campus.love.common.core.domain.EnableProperties;
import com.campus.love.common.core.handle.BaseInterceptorHandle;
import org.springframework.stereotype.Component;

@Component
public class InterceptorHandle extends BaseInterceptorHandle {

    @Override
    public EnableProperties enableProperties() {
        return EnableProperties
                .of()
//                .enableGateway()
//                .enableFeign()
                .enableModule();
    }
}
