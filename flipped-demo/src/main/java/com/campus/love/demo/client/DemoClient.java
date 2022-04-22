package com.campus.love.demo.client;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.module.demo.DemoFeignClient;
import com.campus.love.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoClient implements DemoFeignClient {

    private final DemoService demoService;

    public DemoClient(DemoService demoService) {
        this.demoService = demoService;
    }

    @Override
    public MessageModel<String> demoTest(Integer id) {
        log.info("demoFeign");
        System.out.println(demoService.test());
        // AssertUtil.failed(300,"调用异常");
        return MessageModel.success("demoClient"+id);
    }
}
