package com.campus.love.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.tweet.TweetFeignClient;
import com.campus.love.common.feign.module.tweet.dto.CommentDto;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.message.entity.Notice;
import com.campus.love.message.mapper.NoticeMapper;
import com.campus.love.message.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    private final TweetFeignClient tweetFeignClient;

    public NoticeServiceImpl(NoticeMapper noticeMapper, TweetFeignClient tweetFeignClient) {
        this.noticeMapper = noticeMapper;
        this.tweetFeignClient = tweetFeignClient;
    }


    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void setNoticeRead(Integer noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        AssertUtil.ifNull(notice, "找不到对应的notice");
        notice.setIsRead(ReadState.IS_READ.getNumber());
        int i = noticeMapper.updateById(notice);
        AssertUtil.failed(() -> i == 0, "更新notice表失败");
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void insertNotice(NoticeMqDto noticeMqDto) {
        Notice build = Notice.builder()
                .isRead(noticeMqDto.getReadState().getNumber())
                .messageId(noticeMqDto.getMessageId())
                .messageType(noticeMqDto.getMessageType().getNumber())
                .userId(noticeMqDto.getUserId())
                .build();
        int insert = noticeMapper.insert(build);
        AssertUtil.failed(() -> insert == 0, "插入消息失败");
    }

    @Override
    public List<CommentDto> getCommentList(Integer userId) {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .and(i -> i.eq(Notice::getUserId, userId))
                .eq(Notice::getMessageType, MessageType.COMMENT_COMMENT.getNumber())
                .or()
                .eq(Notice::getMessageType, MessageType.COMMENT_TWEET.getNumber());

        List<Notice> notices = noticeMapper.selectList(queryWrapper);
        return notices.parallelStream().map(m -> {
            Integer messageId = m.getMessageId();
            MessageModel<CommentDto> commentDtoMessageModel = tweetFeignClient.queryComments(messageId);

            CommentDto data = commentDtoMessageModel.getData();
            data.setReadState(m.getIsRead());
            return data;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LikesDto> getLikesList(Integer userId) {
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.and(i -> i.eq(Notice::getUserId, userId))
                .eq(Notice::getMessageType, MessageType.LIKES_COMMENT.getNumber())
                .or()
                .eq(Notice::getMessageType, MessageType.LIKES_TWEET.getNumber());
        List<Notice> notices = noticeMapper.selectList(queryWrapper);

        return notices.parallelStream().map(m -> {
            Integer messageId = m.getMessageId();
            MessageModel<LikesDto> likesDtoMessageModel = tweetFeignClient.queryLikes(messageId);
            LikesDto data = likesDtoMessageModel.getData();
            data.setReadState(m.getIsRead());
            return data;
        }).collect(Collectors.toList());
    }
}
