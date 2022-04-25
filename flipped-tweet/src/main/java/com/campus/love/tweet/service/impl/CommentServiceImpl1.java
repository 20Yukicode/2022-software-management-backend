package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.constant.ChangeType;
import com.campus.love.tweet.enums.Order;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.manager.CommentManager;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.common.core.util.SynchronizedByKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl1 implements CommentService<CommentBo> {

    private final CommentManager commentManager;

    private final CommentMapper commentMapper;

    private final TweetMapper tweetMapper;

    private final SynchronizedByKey<Operator> synchronizedByKey;

    public CommentServiceImpl1(CommentManager commentManager, CommentMapper commentMapper, TweetMapper tweetMapper, SynchronizedByKey<Operator> synchronizedByKey) {
        this.commentManager = commentManager;
        this.commentMapper = commentMapper;
        this.tweetMapper = tweetMapper;
        this.synchronizedByKey = synchronizedByKey;
    }

    //找到某条评论下的所有子评论（直接回复或者间接回复）
    private void recursive(List<CommentBo> list, Integer commentId) {
        if (commentId == null) {
            return;
        }
        commentManager.getAllChildComments(commentId)
                .forEach(item -> {
                    CommentBo commentBo = commentManager.commentToBo(item);
                    list.add(commentBo);
                    recursive(list, item.getId());
                });
    }

    @Override
    public CommentTreeNodeVo<CommentBo> GetAllComments(Integer commentId, Order order) {
        if (commentId == null) {
            return null;
        }
        Comment oneComment = commentManager.getOneComment(commentId);

        CommentBo commentBo = commentManager.commentToBo(oneComment);
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

    public void changeCommentNum(Integer operatorId,OperatorType operatorType,Integer num) {
        switch (operatorType) {
            case TWEET:
                Tweet tweet = tweetMapper.selectById(operatorId);

                tweet.setCommentNum(tweet.getCommentNum() + num);
                tweetMapper.updateById(tweet);
                break;

            case COMMENT:
                Comment comment = commentMapper.selectById(operatorId);

                comment.setCommentNum(comment.getCommentNum() + num);
                commentMapper.updateById(comment);
                break;

            default:
                AssertUtil.failed("不是动态或评论");
        }
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void insertComment(AddCommentVo addVo) {
        Comment.CommentBuilder builder = Comment.builder();
        builder.content(addVo.getContent())
                .likesNum(0)
                .commentNum(0)
                .userId(addVo.getUserId());
        Operator operator = addVo.getOperator();
        Integer operatorId = operator.getOperatorId();
        OperatorType operatorType = operator.getOperatorType();

        synchronizedByKey.exec(operator,()->{
            int insert = commentMapper.insert(builder.build());
            AssertUtil.failed(() -> insert == 0, "插入评论失败");
            changeCommentNum(operatorId, operatorType, ChangeType.ADD);
        });
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void deleteComment(Integer commentId,Operator operator) {
        Integer operatorId = operator.getOperatorId();
        OperatorType operatorType = operator.getOperatorType();

        //todo 这里要加锁
        synchronizedByKey.exec(operator, () -> {
            int i = commentMapper.deleteById(commentId);
            AssertUtil.failed(() -> i != 1, "删除评论失败");
            changeCommentNum(operatorId, operatorType, ChangeType.SUB);
        });
    }
}
