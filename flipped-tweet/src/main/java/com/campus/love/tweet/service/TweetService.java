package com.campus.love.tweet.service;

import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.enums.Order;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.domain.vo.TweetVo;
import com.campus.love.tweet.entity.Comment;

public interface TweetService {

    /**
     * 得到动态内容和本动态的所有直接评论
     *
     * @param tweetId
     * @return
     */
    TweetVo<CommentBo> getCommentsByTweet(Integer tweetId);


    /**
     * 递归查询动态和它下面的所有评论
     * 以及评论的评论，评论的评论最多五条
     * 按照某种规律排序
     * @param tweetId
     * @return
     */
    TweetVo<CommentTreeNodeVo<CommentBo>> getAllCommentsByTweet(Integer tweetId, Order order);
}
