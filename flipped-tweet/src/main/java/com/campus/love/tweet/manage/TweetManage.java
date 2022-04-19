package com.campus.love.tweet.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TweetManage {

    private final TweetMapper tweetMapper;

    private final CommentMapper commentMapper;

    public TweetManage(TweetMapper tweetMapper, CommentMapper commentMapper) {
        this.tweetMapper = tweetMapper;
        this.commentMapper = commentMapper;
    }

    /**
     * 查找Id为tweetId的动态实体
     * @param tweetId
     * @return
     */
    public Tweet getOneTweet(Integer tweetId) {
        if(tweetId==null){
            return null;
        }
        LambdaQueryWrapper<Tweet> queryWrapper = new LambdaQueryWrapper<>();
        Tweet tweet = tweetMapper.selectOne(queryWrapper);
        AssertUtil.ifNull(tweet, "找不到tweetId为" + tweetId + "的动态");
        return tweet;
    }

    /**
     * 根据动态Id去查找第一层评论
     * 看每条评论的父Id
     * @return
     */
    public List<Comment> getCommentsByTweet(Integer tweetId) {
        if (tweetId == null) {
            return null;
        }
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getPTweetId, tweetId);
        return commentMapper.selectList(queryWrapper);
    }
}
