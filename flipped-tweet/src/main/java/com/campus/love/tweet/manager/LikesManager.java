package com.campus.love.tweet.manager;

import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Likes;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import org.springframework.stereotype.Component;

@Component
public class LikesManager {

    private final TweetMapper tweetMapper;

    private final CommentMapper commentMapper;

    public LikesManager(TweetMapper tweetMapper, CommentMapper commentMapper) {
        this.tweetMapper = tweetMapper;
        this.commentMapper = commentMapper;
    }



    public NoticeDto generatorNoticeDto(Likes likes) {
        AssertUtil.ifNull(likes.getId(), "likesId不能为空");
        MessageType messageType = likes.getIsTweet().equals(1) ?
                MessageType.LIKES_TWEET : MessageType.LIKES_COMMENT;
        return NoticeDto.builder()
                .messageId(likes.getId())
                .userId(likes.getUserId())
                .messageType(messageType)
                .readState(ReadState.NOT_READ)
                .createTime(likes.getCreateTime()).build();
    }

    public void changeLikesNum(Integer operatorId, OperatorType operatorType, Integer num) {
        switch (operatorType) {
            case TWEET:
                Tweet tweet = tweetMapper.selectById(operatorId);
                AssertUtil.ifNull(tweet,"不存在该动态");

                tweet.setLikeNum(tweet.getLikeNum() + num);
                tweetMapper.updateById(tweet);
                break;

            case COMMENT:
                Comment comment = commentMapper.selectById(operatorId);
                AssertUtil.ifNull(comment,"不存在该评论");

                comment.setLikesNum(comment.getLikesNum() + num);
                commentMapper.updateById(comment);
                break;

            default:
                AssertUtil.failed("不是动态或评论");
        }
    }
}
