package com.campus.love.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.SplitUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.message.domain.bo.ChatRecordBo;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.manager.ChatRecordManager;
import com.campus.love.message.mapper.ChatRecordMapper;
import com.campus.love.message.service.ChatService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Primary
public class ChatServiceImpl implements ChatService {

    private final ChatRecordMapper chatRecordMapper;

    private final ChatRecordManager chatRecordManager;

    private final UserFeignClient userFeignClient;


    public ChatServiceImpl(ChatRecordMapper chatRecordMapper, ChatRecordManager chatRecordManager, UserFeignClient userFeignClient) {
        this.chatRecordMapper = chatRecordMapper;
        this.chatRecordManager = chatRecordManager;
        this.userFeignClient = userFeignClient;
    }


    private String transferSign(Integer first, Integer second, Integer userId) {
        return Objects.equals(first, userId) ? first + "_" + second : second + "_" + first;
    }

    /**
     * 查找userA和userB所有的聊天记录
     * @param s
     * @param chatRecordList
     * @return
     */
    private ChatRecordBo getChatRecordByTwoUserId(String s, List<ChatRecord> chatRecordList) {
        List<Integer> split = SplitUtil.split(s, "_", Integer.class);
        assert split != null;
        Integer userAId = split.get(0);
        Integer userBId = split.get(1);

        var builder = ChatRecordBo
                .builder()
                .userAId(userAId)
                .userBId(userBId);

        CompletableFuture<Void> getBInfoAsync = CompletableFuture.runAsync(() -> {
            //获取用户B的相关信息
            MessageModel<UserInfoDto> userSomeInfos = userFeignClient
                    .queryUserInfos(userBId);
            UserInfoDto data = userSomeInfos.getData();
            builder.userBName(data.getUserName())
                    .userBAvatar(data.getUserAvatar());
        });

        CompletableFuture<Void> getChatRecordsAsync = CompletableFuture.runAsync(() -> {
            List<ChatRecord> collect = chatRecordList
                    .stream()
                    .sorted(Comparator.comparing(ChatRecord::getCreateTime))
                    .collect(Collectors.toList());
            builder.userChatRecordBoList(collect);
        });

        CompletableFuture.allOf(getBInfoAsync, getChatRecordsAsync).join();

        return builder.build();
    }

    @Override
    public ChatRecordBo getTwoUserChatRecords(Integer userAId, Integer userBId) {
        MessageModel<UserInfoDto> userInfoDto = userFeignClient.queryUserInfos(userBId);
        UserInfoDto data = userInfoDto.getData();
        if (data == null) {
            AssertUtil.failed("不存在Id为" + userBId + "的用户信息");
        }
        return ChatRecordBo.builder()
                .userAId(userAId)
                .userBId(userBId)
                .userBName(data.getUserName())
                .userBAvatar(data.getUserAvatar())
                .userChatRecordBoList(chatRecordManager.getChatRecordsByTwoUser(userAId, userBId))
                .build();
    }

    @Override
    public void insertChatRecord(ChatRecord oneChatRecord) {
        oneChatRecord.setIsRead(ReadState.NOT_READ.getNumber());
        int insert = chatRecordMapper.insert(oneChatRecord);
        AssertUtil.failed(() -> insert == 0, "插入chatRecord表失败");
    }

    @Override
    public void deleteChatRecord(Integer charRecordId) {
        LambdaQueryWrapper<ChatRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatRecord::getId, charRecordId);
        int i = chatRecordMapper.deleteById(charRecordId);
        AssertUtil.failed(() -> i == 0, "输入的聊天记录Id不存在");
    }

    @Override
    public void chatRecordIsRead(Integer chatRecordId) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setIsRead(ReadState.IS_READ.getNumber());
        int update = chatRecordMapper.updateById(chatRecord);
        AssertUtil.failed(() -> update == 0, "输入的聊天记录Id不存在");
    }



    @Override
    public List<ChatRecordBo> getAUserAllChatRecords(Integer userId) {
        List<ChatRecord> chatRecords = chatRecordManager.getChatRecordsByOneUser(userId);

        //对集合按照userAId，userBId分组
        Map<String, List<ChatRecord>> collect = chatRecords
                .parallelStream()
                .collect(Collectors
                        .groupingBy(
                                o -> transferSign(o.getSendUserId(), o.getReceiveUserId(), userId),
                                Collectors.toList())
                );

        //返回最终结果
        return collect.entrySet()
                .parallelStream()
                .map(s -> getChatRecordByTwoUserId(s.getKey(), s.getValue()))
                .collect(Collectors.toList());
    }
}
