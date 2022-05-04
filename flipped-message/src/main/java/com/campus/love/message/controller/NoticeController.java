package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.module.tweet.dto.CommentDto;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.common.mq.domain.dto.LikesMqDto;
import com.campus.love.message.service.NoticeService;
import com.campus.love.message.service.SubscribedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags ="消息模块")
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

    @ApiIgnore
    @ApiOperation("发送系统消息")
    @PostMapping("/systemNotice/userId/{userId}")
    public MessageModel<Object> sendSystemNotice(@PathVariable Integer userId){

        return MessageModel.success();
    }

    @ApiOperation("设置消息已读")
    @PostMapping("/noticeRead")
    public MessageModel<Object> setNoticeRead(@RequestParam("noticeId") Integer noticeId){
        noticeService.setNoticeRead(noticeId);

        return MessageModel.success();
    }

    @ApiOperation("设置消息已读")
    @PostMapping("/noticeReadBatch")
    public MessageModel<Object> setNoticeRead(@RequestParam("noticeId") List<Integer> noticeId) {

        noticeId.parallelStream()
                .forEach(noticeService::setNoticeRead);
        return MessageModel.success();
    }

    @ApiOperation("获取关注信息")
    @GetMapping("/userId/{userId}/subscribed")
    public MessageModel<List<SubscribedUserDto>> querySubscribedList(@PathVariable Integer userId) {
        List<SubscribedUserDto> subscribedList = subscribedService.getSubscribedList(userId);

        return MessageModel.success(subscribedList);
    }


    @ApiOperation("获取评论消息")
    @GetMapping("/userId/{userId}/comment")
    public MessageModel<List<CommentDto>> queryCommentList(@PathVariable Integer userId){

        List<CommentDto> commentList = noticeService.getCommentList(userId);
        return MessageModel.success(commentList);
    }

    @ApiOperation("获取点赞消息")
    @GetMapping("/userId/{userId}/likes")
    public MessageModel<List<LikesDto>> queryLikesList(@PathVariable Integer userId){

        List<LikesDto> likesList = noticeService.getLikesList(userId);
        return MessageModel.success(likesList);
    }

    //todo
    @ApiOperation("获取系统消息")
    @GetMapping("/userId/{userId}/sysNotice")
    public MessageModel<List<Object>> querySystemNoticeList(@PathVariable Integer userId){

        return null;
    }

    //todo
    @ApiOperation("获取举报消息")
    @GetMapping("/userId/{userId}/report")
    public MessageModel<List<Object>> queryReportList(@PathVariable Integer userId){

        return null;
    }



}
