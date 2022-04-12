package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.demo.DemoFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final DemoFeignClient demoFeignClient;

    public NoticeController(DemoFeignClient demoFeignClient){
        this.demoFeignClient=demoFeignClient;
    }

    @GetMapping("")
    public MessageModel<String> notice(){
        Integer id=5;
        var data=demoFeignClient.demoTest(id).getData();
        return MessageModel.success(data);
    }

}
