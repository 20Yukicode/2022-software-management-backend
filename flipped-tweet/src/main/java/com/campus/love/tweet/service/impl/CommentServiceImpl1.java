package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.enums.Order;
import com.campus.love.tweet.domain.enums.Operator;
import com.campus.love.tweet.domain.enums.OperatorType;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.AddVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.manage.CommentManage;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl1 implements CommentService<CommentBo> {

    private final CommentManage commentManage;

    private final CommentMapper commentMapper;

    public CommentServiceImpl1(CommentManage commentManage, CommentMapper commentMapper) {
        this.commentManage = commentManage;
        this.commentMapper = commentMapper;
    }

    //找到某条评论下的所有子评论（直接回复或者间接回复）
    private void recursive(List<CommentBo> list, Integer commentId) {
        if (commentId == null) {
            return;
        }
        commentManage.getAllChildComments(commentId)
                .forEach(item -> {
                    CommentBo commentBo = commentManage.commentToBo(item);
                    list.add(commentBo);
                    recursive(list, item.getId());
                });
    }

    @Override
    public CommentTreeNodeVo<CommentBo> GetAllComments(Integer commentId, Order order) {
        if (commentId == null) {
            return null;
        }
        Comment oneComment = commentManage.getOneComment(commentId);

        CommentBo commentBo = commentManage.commentToBo(oneComment);
        CommentTreeNodeVo<CommentBo> node =
                new CommentTreeNodeVo<>(commentBo, null);
        List<CommentBo> list = new ArrayList<>();

        recursive(list, commentId);

        //根据order对list排序
        if(order!=null){
            //
        }
        node.setChildCommentNodes(list);
        return node;
    }

    @Override
    public void insertComment(AddCommentVo addVo) {
        Comment.CommentBuilder builder = Comment.builder();
        builder.content(addVo.getContent())
                .likesNum(0)
                .commentNum(0)
                .userId(addVo.getUserId());
        Operator operator = addVo.getOperator();
        if (operator.getOperatorType().equals(OperatorType.TWEET)) {
            builder.pTweetId(operator.getOperatorId());
        } else if (operator.getOperatorType().equals(OperatorType.COMMENT)) {
            builder.pCommentId(operator.getOperatorId());
        } else {
            AssertUtil.failed("不是合法的回复类型");
        }
        int insert = commentMapper.insert(builder.build());
        AssertUtil.failed(() -> insert == 0, "插入评论失败");
    }

    @Override
    public void deleteComment(Integer commentId) {
        int i = commentMapper.deleteById(commentId);
        AssertUtil.failed(() -> i != 1, "删除评论失败");
    }
}
