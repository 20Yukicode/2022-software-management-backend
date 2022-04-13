package com.campus.love.common.feign.exception;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RemoteExceptionAspect {

    @Pointcut("execution(public * com.campus.love.common.feign.module..*.*(..))")
    public void handleRemoteResult() {
    }


    @AfterReturning(value = "handleRemoteResult()", returning = "ret")
    public <T> MessageModel<T> after(Object ret) {
        if (ret instanceof MessageModel) {
            MessageModel<T> result = (MessageModel<T>) ret;
            long code = result.getCode();
            if (code != 200) {
                if (code != 500) {
                    AssertUtil.failed(code, result.getMessage());
                }
                AssertUtil.failed(result.getMessage());
            }
            return result;
        }
        throw new ApiException("转换异常");
    }
}
