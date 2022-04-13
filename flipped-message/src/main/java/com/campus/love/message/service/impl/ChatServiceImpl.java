package com.campus.love.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.message.domain.enums.ReadState;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.mapper.ChatRecordMapper;
import com.campus.love.message.service.ChatService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ChatServiceImpl implements ChatService {

    private final ChatRecordMapper chatRecordMapper;

    public ChatServiceImpl(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }

    @Override
    public Page<ChatRecord> getChatRecord(Integer aUserId, Integer bUserId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ChatRecord::getSendUserId, aUserId)
                .eq(ChatRecord::getReceiveUserId, bUserId)
                .or()
                .eq(ChatRecord::getSendUserId, bUserId)
                .eq(ChatRecord::getReceiveUserId, aUserId);

        Page<ChatRecord> page = new Page<>();
        page.setPages(pageNum);
        page.setSize(pageSize);

        return chatRecordMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void insertChatRecord(ChatRecord oneChatRecord) {
        oneChatRecord.setIsRead(ReadState.NOT_READ.getNumber());
        int insert = chatRecordMapper.insert(oneChatRecord);
        if (insert != 1) {
            AssertUtil.failed("插入数据失败");
        }
    }

    @Override
    public void deleteChatRecord(Integer charRecordId) {

        LambdaQueryWrapper<ChatRecord>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatRecord::getId,charRecordId);
        int i = chatRecordMapper.deleteById(charRecordId);
        if(i!=1){
            AssertUtil.isNull("输入的聊天记录Id不存在");
        }
    }

    @Override
    public void chatRecordIsRead(Integer chatRecordId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setIsRead(ReadState.IS_READ.getNumber());
        int update = chatRecordMapper.updateById(chatRecord);
        if (update == 0) {
            AssertUtil.isNull("输入的聊天记录Id不存在");
        }
    }
}
