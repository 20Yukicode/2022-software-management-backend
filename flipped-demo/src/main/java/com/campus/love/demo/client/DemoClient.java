package com.campus.love.demo.client;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.demo.DemoFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoClient implements DemoFeignClient {

    @Override
    public MessageModel<String> demoTest(Integer id) {
        log.info("demoFeign");
        return MessageModel.success("demoClient"+id);
    }
}
