package com.campus.love.demo.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.SynchronizedByKey;
import com.campus.love.demo.domain.enums.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final SynchronizedByKey<Integer> synchronizedByKey;

    private final SynchronizedByKey<Integer> synchronizedByKey1;

    public TestController(SynchronizedByKey<Integer> synchronizedByKey, SynchronizedByKey<Integer> synchronizedByKey1) {
        this.synchronizedByKey = synchronizedByKey;
        this.synchronizedByKey1 = synchronizedByKey1;
    }


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public MessageModel<String> helloWorld() {

        return MessageModel.success("hello world");
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1() {

        return MessageModel.success("hello world");
    }

    @RequestMapping(value = "/enum", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1(@RequestParam("order")Order order) {

        log.info(order.toString());
        return MessageModel.success("hello world");
    }


    @GetMapping("/syn")
    public void testSyn(@RequestParam("userId") Integer userId){

        synchronizedByKey.exec(userId,()->{
            log.info("开始1");
            try{
                Thread.sleep(300);
            }catch (Exception ignored){

            }
            log.info("结束1");
        });
        synchronizedByKey1.exec(userId,()->{
            log.info("开始2");
            try{
                Thread.sleep(100);
            }catch (Exception ignored){

            }
            log.info("结束2");
        });
    }
}
