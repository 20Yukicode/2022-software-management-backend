package com.campus.love.message.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.mapper.ChatRecordMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatRecordManager {

    private final ChatRecordMapper chatRecordMapper;

    public ChatRecordManager(ChatRecordMapper chatRecordMapper) {
        this.chatRecordMapper = chatRecordMapper;
    }

    /**
     * 查找两个人聊天记录
     * @param userAId
     * @param userBId
     * @return
     */
    public List<ChatRecord> getChatRecordsByTwoUser(int userAId, int userBId) {
        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(ChatRecord::getSendUserId, userAId)
                .eq(ChatRecord::getReceiveUserId, userBId)
                .or()
                .eq(ChatRecord::getSendUserId, userBId)
                .eq(ChatRecord::getReceiveUserId, userAId);
        return chatRecordMapper.selectList(queryWrapper).parallelStream()
                .sorted((a, b) -> (int) (b.getCreateTime().getTime() - a.getCreateTime().getTime()))
                .collect(Collectors.toList());
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
