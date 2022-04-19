package com.campus.love.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.message.domain.bo.ChatRecordBo;
import com.campus.love.message.domain.bo.UserChatRecordBo;
import com.campus.love.message.domain.enums.ReadState;
import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.manage.ChatRecordManage;
import com.campus.love.message.mapper.ChatRecordMapper;
import com.campus.love.message.service.ChatService;
import com.campus.love.common.core.util.SplitUtil;
import com.campus.love.message.util.ThreadUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Primary
public class ChatServiceImpl implements ChatService {

    private final ChatRecordMapper chatRecordMapper;

    private final ChatRecordManage chatRecordManage;

    private final UserFeignClient userFeignClient;

    /**
     * 手动创建线程池
     */
    private static final ThreadPoolExecutor executor = new  ThreadPoolExecutor(20,25,100L,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy());

    public ChatServiceImpl(ChatRecordMapper chatRecordMapper, ChatRecordManage chatRecordManage, UserFeignClient userFeignClient) {
        this.chatRecordMapper = chatRecordMapper;
        this.chatRecordManage = chatRecordManage;
        this.userFeignClient = userFeignClient;
    }



    @Override
    public Page<ChatRecord> getAUserAllChatRecords(Integer userAId, Integer userBId, Integer pageNum, Integer pageSize) {

        Page<ChatRecord> page = new Page<>();
        page.setPages(pageNum);
        page.setSize(pageSize);

        return chatRecordMapper.selectPage(page, chatRecordManage.getChatRecordsByTwoUser(userAId, userBId));
    }

    @Override
    public void insertChatRecord(ChatRecord oneChatRecord) {
        oneChatRecord.setIsRead(ReadState.NOT_READ.getNumber());
        int insert = chatRecordMapper.insert(oneChatRecord);
        AssertUtil.failed(() -> insert != 1, "插入数据失败");
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


    /**
     * 查找userA和userB所有的聊天记录
     * @param s
     * @param chatRecordList
     * @return
     */
    private ChatRecordBo getChatRecordByTwoUserId(String s, List<ChatRecord> chatRecordList) {
        List<Integer> split = SplitUtil.split(s, "_",Integer.class);
        assert split != null;
        Integer userAId = split.get(0);
        Integer userBId = split.get(1);

        var builder = ChatRecordBo
                .builder()
                .userAId(userAId)
                .userBId(userBId);

        List<Runnable> o = List.of(() -> {
            //获取用户B的相关信息
            MessageModel<UserInfoDto> userSomeInfos = userFeignClient
                    .queryUserInfos(userBId);
            UserInfoDto data = userSomeInfos.getData();
            builder.userBName(data.getUserName())
                    .userBAvatar(data.getUserAvatar());
        }, () -> {
            //获取两个人的所有聊天消息
            List<UserChatRecordBo> userChatRecordBoList =
                    chatRecordList
                    .stream()
                    .map(m -> UserChatRecordBo.builder()
                            .chatRecordId(m.getId())
                            .content(m.getContent())
                            .isRead(m.getIsRead())
                            .createTime(m.getCreateTime())
                            .updateTime(m.getUpdateTime())
                            .build())
                    .sorted(Comparator.comparing(UserChatRecordBo::getCreateTime))
                    .collect(Collectors.toList());
            builder.userChatRecordBoList(userChatRecordBoList);
        });

        ThreadUtil.run(executor, o);

        return builder.build();
    }

    private String transferSign(Integer first, Integer second, Integer userId) {
        return Objects.equals(first, userId) ? first + "_" + second : second + "_" + first;
    }


    @Override
    public List<ChatRecordBo> getAUserAllChatRecords(Integer userId) {
        List<ChatRecord> chatRecords = chatRecordManage.getChatRecordsByOneUser(userId);


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
