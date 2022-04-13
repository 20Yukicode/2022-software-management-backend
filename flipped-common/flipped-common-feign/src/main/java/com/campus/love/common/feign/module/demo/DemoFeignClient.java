package com.campus.love.common.feign.module.demo;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.domain.FeignConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("flipped-demo")
public interface DemoFeignClient {

    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX+"/{id}")
    MessageModel<String> demoTest(@PathVariable Integer id);
}
