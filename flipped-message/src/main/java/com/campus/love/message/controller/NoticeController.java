package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.module.demo.DemoFeignClient;
import com.campus.love.common.feign.module.tweet.TweetFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    private final DemoFeignClient demoFeignClient;

    private final TweetFeignClient tweetFeignClient;

    public NoticeController(DemoFeignClient demoFeignClient, TweetFeignClient tweetFeignClient) {
        this.demoFeignClient = demoFeignClient;
        this.tweetFeignClient = tweetFeignClient;
    }

    @GetMapping("")
    public MessageModel<String> notice() {
        Integer id = 5;
        var data = demoFeignClient.demoTest(id);
        String data1 = data.getData();
        log.info(data.toString());
        return MessageModel.success(data1);
    }


    @PostMapping("")
    public MessageModel notices(){
        return null;
    }



}
