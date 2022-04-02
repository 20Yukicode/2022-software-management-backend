package com.campus.love.user.interceptor;

import com.campus.love.common.core.handle.BaseInterceptorHandle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterceptorHandle extends BaseInterceptorHandle {

    @Override
    public List<String> whiteList() {
        return List.of("/*/login");
    }
}
