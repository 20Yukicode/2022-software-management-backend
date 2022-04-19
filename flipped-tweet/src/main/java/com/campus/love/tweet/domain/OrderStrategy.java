package com.campus.love.tweet.domain;

import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.entity.Comment;

import java.util.List;

/**
 * 排序策略
 * 阐述一些我的想法：
 * 对于第一层回复，作者回复自己的动态要放在前面，然后根据点赞数来排序？再根据评论数来排序，
 */
public class OrderStrategy {

    /**
     * 评论子列表
     * 发布动态人的Id
     * @param comments
     * @param tweetUserId
     * @return
     */
    public static List<CommentBo> orderComments(List<CommentBo> comments,Integer tweetUserId){

        comments.forEach(commentBo->{
            Comment comment = commentBo.getComment();

            Integer sendUserId = commentBo.getSendUserInfo().getUserId();
            Integer commentNum = comment.getCommentNum();
            Integer userId = comment.getUserId();
        });
        return null;
    }
}
