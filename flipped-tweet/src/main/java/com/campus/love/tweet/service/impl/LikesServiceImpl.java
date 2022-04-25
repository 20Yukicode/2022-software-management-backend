package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.constant.ChangeType;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.domain.vo.AddLikesVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Likes;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.LikesMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import com.campus.love.tweet.service.LikesService;
import com.campus.love.common.core.util.SynchronizedByKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesMapper likesMapper;

    private final CommentMapper commentMapper;

    private final TweetMapper tweetMapper;

    private final SynchronizedByKey<Operator> synchronizedByKey;

    public LikesServiceImpl(LikesMapper likesMapper, CommentMapper commentMapper, TweetMapper tweetMapper, SynchronizedByKey<Operator> synchronizedByKey) {
        this.likesMapper = likesMapper;
        this.commentMapper = commentMapper;
        this.tweetMapper = tweetMapper;
        this.synchronizedByKey = synchronizedByKey;
    }


    public void changeLikesNum(Integer operatorId,OperatorType operatorType,Integer num) {
        switch (operatorType) {
            case TWEET:
                Tweet tweet = tweetMapper.selectById(operatorId);

                tweet.setLikeNum(tweet.getLikeNum() + num);
                tweetMapper.updateById(tweet);
                break;

            case COMMENT:
                Comment comment = commentMapper.selectById(operatorId);

                comment.setLikesNum(comment.getLikesNum() + num);
                commentMapper.updateById(comment);
                break;

            default:
                AssertUtil.failed("不是动态或评论");
        }
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void likeComment(AddLikesVo addLikesVo) {
        Operator operator = addLikesVo.getOperator();
        Integer operatorId = operator.getOperatorId();
        OperatorType operatorType = operator.getOperatorType();

        Likes build = Likes.builder()
                .likedId(operatorId)
                .isTweet(operatorType.getCode())
                .userId(addLikesVo.getUserId())
                .build();

        //todo 这里要加锁,并且是对operatorId，operatorType相同的情况下加锁
        synchronizedByKey.exec(operator, () -> {
            int insert = likesMapper.insert(build);
            AssertUtil.failed(() -> insert == 0, "点赞失败");
            changeLikesNum(operatorId, operatorType, ChangeType.ADD);
        });
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void unlikeComment(Integer likeId, Operator operator) {
        Integer operatorId = operator.getOperatorId();
        OperatorType operatorType = operator.getOperatorType();

        //todo 这里要加锁
        synchronizedByKey.exec(operator, () -> {
            int i = likesMapper.deleteById(likeId);
            AssertUtil.failed(() -> i == 0, "取消点赞失败");
            changeLikesNum(operatorId, operatorType, ChangeType.SUB);
        });
    }

}
