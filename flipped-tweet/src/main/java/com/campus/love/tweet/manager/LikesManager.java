package com.campus.love.tweet.manager;

import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
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



    public NoticeMqDto generatorNoticeDto(Likes likes) {
        AssertUtil.ifNull(likes.getId(), "likesId不能为空");

        //如果评论Id为空，必定是点赞动态
        MessageType messageType = likes.getCommentId() == null ?
                MessageType.LIKES_TWEET : MessageType.LIKES_COMMENT;

        return NoticeMqDto.builder()
                .messageId(likes.getId())
                .userId(likes.getUserId())
                .messageType(messageType)
                .readState(ReadState.NOT_READ)
                .createTime(likes.getCreateTime()).build();
    }

    public void changeLikesNum(Integer tweetId,Integer commentId, OperatorType operatorType, Integer num) {
        switch (operatorType) {
            case TWEET:
                Tweet tweet = tweetMapper.selectById(tweetId);
                AssertUtil.ifNull(tweet, "不存在该动态");

                tweet.setLikeNum(tweet.getLikeNum() + num);
                tweetMapper.updateById(tweet);
                break;

            case COMMENT:
                Comment comment = commentMapper.selectById(commentId);
                AssertUtil.ifNull(comment, "不存在该评论");

                comment.setLikesNum(comment.getLikesNum() + num);
                commentMapper.updateById(comment);
                break;

            default:
                AssertUtil.failed("不是动态或评论");
        }
    }
}
