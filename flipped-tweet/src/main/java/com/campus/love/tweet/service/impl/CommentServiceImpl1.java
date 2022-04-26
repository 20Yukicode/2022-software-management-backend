package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.constant.ChangeType;
import com.campus.love.tweet.enums.Order;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.manager.CommentManager;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.common.core.util.SynchronizedByKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl1 implements CommentService<CommentBo> {

    private final SynchronizedByKey<Operator> synchronizedByKey;

    private final CommentManager commentManager;

    private final CommentMapper commentMapper;

    private final RabbitTemplate rabbitTemplate;


    public CommentServiceImpl1(CommentManager commentManager, CommentMapper commentMapper, SynchronizedByKey<Operator> synchronizedByKey, RabbitTemplate rabbitTemplate) {
        this.commentManager = commentManager;
        this.commentMapper = commentMapper;
        this.synchronizedByKey = synchronizedByKey;
        this.rabbitTemplate = rabbitTemplate;
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

        List<CommentBo> sortedList = list.stream()
                .sorted(Order.strategy(order))
                .collect(Collectors.toList());
        sortedList.forEach((item)->{
            System.out.println(item.getComment().getCreateTime());
        });
        node.setChildCommentNodes(sortedList);
        return node;
    }


    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void insertComment(AddCommentVo addVo) {
        Comment.CommentBuilder builder = Comment.builder();
        Operator operator = addVo.getOperator();
        Integer tweetOrCommentId = operator.getTweetOrCommentId();
        OperatorType operatorType = operator.getOperatorType();
        builder.content(addVo.getContent())
                .likesNum(0)
                .commentNum(0)
                .userId(addVo.getUserId());

        switch (operatorType) {
            case TWEET:
                builder.pTweetId(tweetOrCommentId);
                break;
            case COMMENT:
                builder.pCommentId(tweetOrCommentId);
                break;
            default:
                AssertUtil.failed("评论类型错误");
        }

        synchronizedByKey.exec(operator, () -> {
            Comment build = builder.build();
            int insert = commentMapper.insert(build);
            AssertUtil.failed(() -> insert == 0, "插入comment表失败");

            log.info(build.toString());

            String exchange = TweetConstant.TWEET_EXCHANGE;
            String key = TweetConstant.COMMENT_KEY;
            NoticeDto noticeDto = commentManager.generatorNoticeDto(build);
            rabbitTemplate.convertAndSend(exchange, key, noticeDto);

            commentManager.changeCommentNum(tweetOrCommentId, operatorType, ChangeType.ADD);
        });
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void deleteComment(Integer commentId,Operator operator) {
        Integer operatorId = operator.getTweetOrCommentId();
        OperatorType operatorType = operator.getOperatorType();


        synchronizedByKey.exec(operator, () -> {
            int i = commentMapper.deleteById(commentId);
            AssertUtil.failed(() -> i != 1, "删除评论失败");

            commentManager.changeCommentNum(operatorId, operatorType, ChangeType.SUB);
        });
    }
}
