package com.campus.love.common.feign.message;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("flipped-message")
public interface MessageFeignClient {


}
