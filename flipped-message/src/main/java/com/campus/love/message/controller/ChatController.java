package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.message.domain.bo.ChatRecordBo;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "聊天模块")
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @ApiOperation("查找与某个人有关的所有聊天记录")
    @GetMapping("/{userId}")
    public MessageModel<List<ChatRecordBo>> queryChatRecords(@PathVariable Integer userId) {

        List<ChatRecordBo> chatRecords = chatService.getAUserAllChatRecords(userId);

        return MessageModel.success(chatRecords);
    }

    @ApiOperation("查找两个人聊天记录")
    @GetMapping("/{userAId}/{userBId}")
    public MessageModel<ChatRecordBo> queryTwoUserChatRecords(@PathVariable Integer userAId,
                                                              @PathVariable Integer userBId) {

        ChatRecordBo twoUserChatRecords = chatService.getTwoUserChatRecords(userAId, userBId);
        return MessageModel.success(twoUserChatRecords);
    }


    @ApiOperation("添加一条聊天记录")
    @PostMapping("")
    public MessageModel<Object> addChatRecord(@RequestBody ChatRecord oneChatRecord) {

        chatService.insertChatRecord(oneChatRecord);

        return MessageModel.success();
    }

    @ApiOperation("删除一条聊天记录")
    @DeleteMapping("/{chatRecordId}")
    public MessageModel<Object> removeChatRecord(@PathVariable Integer chatRecordId){
        chatService.deleteChatRecord(chatRecordId);

        return MessageModel.success();
    }

    @ApiOperation("设置一条聊天记录为已读")
    @PostMapping("/setRead/{chatRecordId}")
    public MessageModel<Object> setChatRecordRead(@PathVariable Integer chatRecordId) {

        chatService.chatRecordIsRead(chatRecordId);

        return MessageModel.success();
    }
}
