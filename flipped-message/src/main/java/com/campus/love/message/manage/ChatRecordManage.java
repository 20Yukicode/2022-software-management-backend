package com.campus.love.message.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.mapper.ChatRecordMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChatRecordManage {

    private final ChatRecordMapper chatRecordMapper;

    public ChatRecordManage(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }

    /**
     * 查找两个人的聊天记录
     *
     * @param userAId
     * @param userBId
     * @return
     */
    public LambdaQueryWrapper<ChatRecord> getChatRecordsByTwoUser(int userAId, int userBId) {
        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ChatRecord::getSendUserId, userAId)
                .eq(ChatRecord::getReceiveUserId, userBId)
                .or()
                .eq(ChatRecord::getSendUserId, userBId)
                .eq(ChatRecord::getReceiveUserId, userAId);
        return queryWrapper;
    }

    /**
     * 查找与userId相关的所有聊天记录
     * @param userId
     * @return
     */

    public  List<ChatRecord> getChatRecordsByOneUser(Integer userId) {
        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ChatRecord::getSendUserId, userId)
                .or()
                .eq(ChatRecord::getReceiveUserId, userId);

        return chatRecordMapper.selectList(queryWrapper);

        // AssertUtil.ifNull(chatRecords, "userId为" + userId + "的用户不存在");
    }
}
