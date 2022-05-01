package com.campus.love.message.service;


import com.campus.love.message.domain.bo.ChatRecordBo;
import com.campus.love.message.entity.ChatRecord;

import java.util.List;


public interface ChatService {

    ChatRecordBo getTwoUserChatRecords(Integer userAId,Integer userBId);
    /**
     * 插入聊天记录
     */
    void insertChatRecord(ChatRecord oneChatRecord);

    void deleteChatRecord(Integer charRecordId);

    void chatRecordIsRead(Integer chatRecordId);

    List<ChatRecordBo> getAUserAllChatRecords(Integer userId);

}
