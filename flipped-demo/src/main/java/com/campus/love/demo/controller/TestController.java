package com.campus.love.demo.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.demo.domain.enums.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

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
}
