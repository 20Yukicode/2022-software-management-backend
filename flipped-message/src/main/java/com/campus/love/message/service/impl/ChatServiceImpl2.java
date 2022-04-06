package com.campus.love.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.message.domain.enums.ReadState;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.mapper.ChatRecordMapper;
import com.campus.love.message.service.ChatService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ChatServiceImpl2 implements ChatService {

    private final ChatRecordMapper chatRecordMapper;

    public ChatServiceImpl2(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }

    @Override
    public List<ChatRecord> getChatRecord(Integer sendUserId, Integer receiveUserId) {

        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatRecord::getSendUserId, sendUserId);
        queryWrapper.eq(ChatRecord::getReceiveUserId, receiveUserId);

        return chatRecordMapper.selectList(queryWrapper);
    }

    @Override
    public void insertChatRecord(ChatRecord oneChatRecord) {
        oneChatRecord.setIsRead(ReadState.NOT_READ.getNumber());
        int insert = chatRecordMapper.insert(oneChatRecord);
        if(insert!=1){
            AssertUtil.failed("插入数据失败");
        }
    }

    @Override
    public void chatRecordIsRead(Integer chatRecordId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setIsRead(ReadState.IS_READ.getNumber());
        int update = chatRecordMapper.updateById(chatRecord);
        if(update==0){
            AssertUtil.failed(400,"输入的聊天记录Id不存在");
        }
    }
}
