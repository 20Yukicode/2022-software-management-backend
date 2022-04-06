package com.campus.love.message.service;


import com.campus.love.message.entity.ChatRecord;

import java.util.List;


public interface ChatService {

    List<ChatRecord> getChatRecord(Integer sendUserId, Integer receiveUserId);

    default void insertChatRecord(Integer sendUserId, Integer receiveUserId,ChatRecord oneChatRecord){}

    default void insertChatRecord(ChatRecord oneChatRecord){}

    default void chatRecordIsRead(Integer chatRecordId){}
}
