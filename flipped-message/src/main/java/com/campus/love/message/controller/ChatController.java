package com.campus.love.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.message.domain.vo.ChatRecordVo;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping("")
    public MessageModel<Page<ChatRecord>> queryChatRecords(@RequestBody @Validated ChatRecordVo chatRecordVo) {
        log.info(chatRecordVo.toString());
        Integer userAId = chatRecordVo.getUserAId();
        Integer userBId = chatRecordVo.getUserBId();

        Integer pageNum = chatRecordVo.getPageNum();
        Integer pageSize = chatRecordVo.getPageSize();

        return MessageModel.success(chatService.getChatRecord(userAId, userBId, pageNum, pageSize));
    }

    @PostMapping("/add")
    public MessageModel<Object> addChatRecord(@RequestBody ChatRecord oneChatRecord) {

        chatService.insertChatRecord(oneChatRecord);

        return MessageModel.success();
    }

    @DeleteMapping("/chatRecordId/{chatRecordId}")
    public MessageModel<Object> removeChatRecord(@PathVariable Integer chatRecordId){
        chatService.deleteChatRecord(chatRecordId);

        return MessageModel.success();
    }

    @PostMapping("/chatRecordId/{chatRecordId}")
    public MessageModel<Object> setChatRecordRead(@PathVariable Integer chatRecordId) {
        chatService.chatRecordIsRead(chatRecordId);
        return MessageModel.success();
    }
}
