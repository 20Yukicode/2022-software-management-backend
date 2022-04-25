package com.campus.love.tweet.service;


import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.Order;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;


public interface CommentService<T> {

    /**
     * 递归查询评论以及子评论
     * @param commentId
     * @return
     */
    CommentTreeNodeVo<T> GetAllComments(Integer commentId, Order order);

    /**
     * 添加一条评论
     * @param comment
     */
    void insertComment(AddCommentVo comment);

    /**
     * 移除一条评论（不考虑级联删除）
     * @param commentId
     */
    void deleteComment(Integer commentId, Operator operator);



}
