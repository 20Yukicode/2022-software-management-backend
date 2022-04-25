package com.campus.love.tweet.service.impl;

import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.Order;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.manager.CommentManager;
import com.campus.love.tweet.manager.TweetManager;
import com.campus.love.tweet.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService<CommentTreeNodeVo> {

    private final CommentManager commentManager;

    private final TweetManager tweetManager;

    public CommentServiceImpl(CommentManager commentManager, TweetManager tweetManager) {
        this.commentManager = commentManager;
        this.tweetManager = tweetManager;
    }


    private CommentTreeNodeVo recursive(Integer commentId) {
        if (commentId == null) {
            return null;
        }
        Comment oneComment = commentManager.getOneComment(commentId);

        CommentBo commentBo = commentManager.commentToBo(oneComment);
        CommentTreeNodeVo<CommentTreeNodeVo<?>> node =
                new CommentTreeNodeVo<>(commentBo, null);
        List<CommentTreeNodeVo<?>> list = new ArrayList<>();

        commentManager.getAllChildComments(commentId)
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
