package com.campus.love.message.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.message.domain.bo.ChatRecordBo;
import com.campus.love.message.entity.ChatRecord;

import java.util.List;


public interface ChatService {

    Page<ChatRecord> getAUserAllChatRecords(Integer userAId, Integer userBId, Integer pageNum, Integer pageSize);

    /**
     * 插入聊天记录
     * @param oneChatRecord
     */
    void insertChatRecord(ChatRecord oneChatRecord);

    void deleteChatRecord(Integer charRecordId);

    void chatRecordIsRead(Integer chatRecordId);

    List<ChatRecordBo> getAUserAllChatRecords(Integer userId);

}
