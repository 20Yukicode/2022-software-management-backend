package com.campus.love.demo.controller;

import com.campus.love.common.core.api.MessageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public MessageModel<String> helloWorld() {
        return MessageModel.success("hello world");
    }
}
