package com.campus.love.common.feign.module.message;

import com.campus.love.common.core.api.MessageModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("flipped-message")
public interface MessageFeignClient {

    @GetMapping
    MessageModel<Object> xxx();
}
