package com.campus.love.tweet.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.HttpUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CommentManager {

    private final CommentMapper commentMapper;

    private final TweetMapper tweetMapper;

    private final UserFeignClient userFeignClient;

    public CommentManager(CommentMapper commentMapper, TweetMapper tweetMapper, UserFeignClient userFeignClient) {
        this.commentMapper = commentMapper;
        this.tweetMapper = tweetMapper;
        this.userFeignClient = userFeignClient;
    }

    public NoticeMqDto generatorNoticeDto(Comment comment) {
        AssertUtil.ifNull(comment.getId(), "commentId不能为空");

        //如果父commentId为空，那么一定是对动态评论的
        MessageType messageType = comment.getPCommentId() == null ?
                MessageType.COMMENT_TWEET : MessageType.COMMENT_COMMENT;
        return NoticeMqDto.builder()
                .messageId(comment.getId())
                .userId(comment.getUserId())
                .messageType(messageType)
                .readState(ReadState.NOT_READ)
                .createTime(comment.getCreateTime()).build();
    }

    /**
     * 改变评论的数字
     * @param commentId
     * @param tweetId
     * @param operatorType
     * @param num
     */
    public void changeCommentNum(Integer tweetId,Integer commentId, OperatorType operatorType, Integer num) {
        switch (operatorType) {
            case TWEET:
                
                //todo 这里记得用redis
                Tweet tweet = tweetMapper.selectById(tweetId);
                AssertUtil.ifNull(tweet,"不存在该动态");

                tweet.setCommentNum(tweet.getCommentNum() + num);
                tweetMapper.updateById(tweet);
                break;

            case COMMENT:
                Comment comment = commentMapper.selectById(commentId);
                AssertUtil.ifNull(comment,"不存在该评论");

                comment.setCommentNum(comment.getCommentNum() + num);
                commentMapper.updateById(comment);
                break;

            default:
                AssertUtil.failed("不是动态或评论");
        }
    }

    /**
     * 将comment转为commentBo
     * @param comment
     * @return
     */
    public CommentBo commentToBo(Comment comment) {
        if (comment == null) {
            AssertUtil.failed("comment不能为空");
        }
        CommentBo.CommentBoBuilder builder = CommentBo.builder();
        Integer receiveUserId = null;
        Integer pCommentId = comment.getPCommentId();
        Integer pTweetId = comment.getPTweetId();


        //分两种情况，
        //第一种情况是父节点是评论
        //第二种是父节点是动态
        if(pCommentId != null){
            LambdaQueryWrapper<Comment> commentLambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            commentLambdaQueryWrapper.eq(Comment::getId, pCommentId);
            Comment comment1 = commentMapper.selectOne(commentLambdaQueryWrapper);
            receiveUserId = comment1.getUserId();
        }
        else if (pTweetId != null) {
            LambdaQueryWrapper<Tweet> tweetLambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            tweetLambdaQueryWrapper.eq(Tweet::getId, pTweetId);
            Tweet tweet = tweetMapper.selectOne(tweetLambdaQueryWrapper);
            receiveUserId = tweet.getUserId();
        }  else {
            AssertUtil.failed("pTweetId和pCommentId不能同时为空");
        }

        HttpUtil.setInheritable();
        CompletableFuture<Void> sendFuture = CompletableFuture
                .supplyAsync(() -> userFeignClient.queryUserInfos(comment.getUserId()))
                .thenAccept((data) -> builder.sendUserInfo(data.getData()));


        Integer finalReceiveUserId = receiveUserId;
        CompletableFuture<Void> receiveFuture = CompletableFuture
                .supplyAsync(() -> userFeignClient.queryUserInfos(finalReceiveUserId))
                .thenAccept((data) -> builder.receiveUserInfo(data.getData()));

        CompletableFuture.allOf(sendFuture, receiveFuture).join();

        return builder.comment(comment).build();
    }
    /**
     * 查询id为commentId的评论实体
     * @param commentId
     * @return
     */
    public Comment getOneComment(Integer commentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getId, commentId);
        Comment comment = commentMapper.selectOne(queryWrapper);
        if(comment==null){
            AssertUtil.failed("id为"+commentId+"的评论不存在");
        }
        return comment;
    }

    /**
     * 查询pid为commentId的评论实体列表
     * 也就是得到某个id的所有子评论
     * @param commentId
     * @return
     */
    public List<Comment> getAllChildComments(Integer commentId) {
        if (commentId == null) {
            return null;
        }
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper
                .eq(Comment::getPCommentId, commentId);
        return commentMapper.selectList(commentLambdaQueryWrapper);
    }






}
