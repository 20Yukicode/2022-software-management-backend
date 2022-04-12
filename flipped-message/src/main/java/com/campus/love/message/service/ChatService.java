package com.campus.love.message.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.message.entity.ChatRecord;


public interface ChatService {

    Page<ChatRecord> getChatRecord(Integer aUserId, Integer bUserId, Integer pageNum, Integer pageSize);

    void insertChatRecord(ChatRecord oneChatRecord);

    void deleteChatRecord(Integer charRecordId);

    void chatRecordIsRead(Integer chatRecordId);

}
