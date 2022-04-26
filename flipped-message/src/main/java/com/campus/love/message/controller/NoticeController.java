package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.message.service.NoticeService;
import com.campus.love.message.service.SubscribedService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Slf4j
public class NoticeController {

    private final SubscribedService subscribedService;

    private final NoticeService noticeService;

    public NoticeController(SubscribedService subscribedService, NoticeService noticeService) {
        this.subscribedService = subscribedService;
        this.noticeService = noticeService;
    }

    @ApiOperation("发送系统消息")
    @PostMapping("/systemNotice/userId/{userId}")
    public MessageModel<Object> sendSystemNotice(@PathVariable Integer userId){

        return MessageModel.success();
    }

    @ApiOperation("获取历史关注信息")
    @GetMapping("/userId/{userId}/subscribed")
    public MessageModel<List<SubscribedUserDto>> getSubscribedList(@PathVariable Integer userId) {
        List<SubscribedUserDto> subscribedList = subscribedService.getSubscribedList(userId);

        return MessageModel.success(subscribedList);
    }

    @ApiOperation("设置消息已读")
    @PostMapping("/noticeId/{noticeId}/noticeRead")
    public MessageModel<Object> setNoticeRead(@PathVariable Integer noticeId){
        noticeService.setNoticeRead(noticeId);

        return MessageModel.success();
    }




}
