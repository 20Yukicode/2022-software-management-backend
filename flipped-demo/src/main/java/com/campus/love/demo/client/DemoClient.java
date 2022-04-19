package com.campus.love.demo.client;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.HttpUtil;
import com.campus.love.common.feign.module.demo.DemoFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class DemoClient implements DemoFeignClient {

    @Override
    public MessageModel<String> demoTest(Integer id) {
        log.info("demoFeign");
       // AssertUtil.failed(300,"调用异常");
        return MessageModel.success("demoClient"+id);
    }
}
