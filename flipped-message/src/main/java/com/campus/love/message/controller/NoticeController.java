package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.module.tweet.TweetFeignClient;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.message.service.notice.SubscribedService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    private final TweetFeignClient tweetFeignClient;

    private final SubscribedService subscribedService;

    public NoticeController(TweetFeignClient tweetFeignClient,  SubscribedService subscribedService) {
        this.tweetFeignClient = tweetFeignClient;
        this.subscribedService = subscribedService;
    }

    @ApiOperation("获取历史关注信息")
    @GetMapping("/{userId}/subscribed")
    public MessageModel<List<SubscribedUserDto>> getSubscribedList(@PathVariable Integer userId) {
        List<SubscribedUserDto> subscribedList = subscribedService.getSubscribedList(userId);

        return MessageModel.success(subscribedList);
    }

    @ApiOperation("设置消息已读")
    @PostMapping("/{userId}/noticeRead")
    public MessageModel<Object> setNoticeRead(@PathVariable Integer userId){
        return null;
    }




}
