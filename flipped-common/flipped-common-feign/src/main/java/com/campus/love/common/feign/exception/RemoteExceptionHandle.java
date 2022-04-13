package com.campus.love.common.feign.exception;

import com.campus.love.common.core.api.MessageModel;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RemoteExceptionHandle {

    /**
     * feign远程调用异常
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public MessageModel<Object> feignException(FeignException e){
        log.info("远程调用异常:"+e.getMessage());
        return MessageModel.feignFailed();
    }
}
