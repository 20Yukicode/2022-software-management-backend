package com.campus.love.tweet.service.impl;

import com.campus.love.tweet.domain.enums.Operator;
import com.campus.love.tweet.domain.enums.Order;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.manage.CommentManage;
import com.campus.love.tweet.manage.TweetManage;
import com.campus.love.tweet.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService<CommentTreeNodeVo> {

    private final CommentManage commentManage;

    private final TweetManage tweetManage;

    public CommentServiceImpl( CommentManage commentManage, TweetManage tweetManage) {
        this.commentManage = commentManage;
        this.tweetManage = tweetManage;
    }


    private CommentTreeNodeVo recursive(Integer commentId) {
        if (commentId == null) {
            return null;
        }
        Comment oneComment = commentManage.getOneComment(commentId);

        CommentBo commentBo = commentManage.commentToBo(oneComment);
        CommentTreeNodeVo<CommentTreeNodeVo<?>> node =
                new CommentTreeNodeVo<>(commentBo, null);
        List<CommentTreeNodeVo<?>> list = new ArrayList<>();

        commentManage.getAllChildComments(commentId)
                .forEach(item -> {
                    CommentTreeNodeVo<CommentTreeNodeVo> comments =
                            recursive(item.getId());
                    list.add(comments);
                });
        node.setChildCommentNodes(list);
        return node;
    }


    @Override
    public CommentTreeNodeVo GetAllComments(Integer commentId, Order order) {
        CommentTreeNodeVo commentTreeNodeVo = recursive(commentId);

        if(order!=null){
            //xxx
        }
        return commentTreeNodeVo;
    }

    @Override
    public void insertComment(AddCommentVo comment) {

    }

    @Override
    public void deleteComment(Integer commentId, Operator operator) {

    }


}
