package com.campus.love.message.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{firstUserId}/{secondUserId}")
    public MessageModel<List<ChatRecord>> queryChatRecords(@PathVariable Integer firstUserId,
                                                           @PathVariable Integer secondUserId) {

        return MessageModel.success(chatService.getChatRecord(firstUserId, secondUserId));
    }

    @PostMapping("")
    public MessageModel<Object> addChatRecord(@RequestBody ChatRecord oneChatRecord) {
        chatService.insertChatRecord(oneChatRecord);
        return MessageModel.success();
    }

    @PostMapping("/chatRecordId/{chatRecordId}")
    public MessageModel<Object> setChatRecordRead(@PathVariable Integer chatRecordId) {
        chatService.chatRecordIsRead(chatRecordId);
        return MessageModel.success();
    }
}
