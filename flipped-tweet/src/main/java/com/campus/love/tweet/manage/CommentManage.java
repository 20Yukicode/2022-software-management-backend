package com.campus.love.tweet.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentManage {

    private final CommentMapper commentMapper;

    private final TweetMapper tweetMapper;

    private final UserFeignClient userFeignClient;

    public CommentManage(CommentMapper commentMapper, TweetMapper tweetMapper, UserFeignClient userFeignClient) {
        this.commentMapper = commentMapper;
        this.tweetMapper = tweetMapper;
        this.userFeignClient = userFeignClient;
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
        Integer receiveUserId;
        Integer pCommentId = comment.getPCommentId();
        //分两种情况，第一种是父节点是动态
        //第二种情况是父节点是评论
        if (pCommentId == null) {
            LambdaQueryWrapper<Tweet> tweetLambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            tweetLambdaQueryWrapper.eq(Tweet::getId, comment.getPTweetId());
            Tweet tweet = tweetMapper.selectOne(tweetLambdaQueryWrapper);
            receiveUserId = tweet.getUserId();
        } else {
            LambdaQueryWrapper<Comment> commentLambdaQueryWrapper =
                    new LambdaQueryWrapper<>();
            commentLambdaQueryWrapper.eq(Comment::getId, pCommentId);
            Comment comment1 = commentMapper.selectOne(commentLambdaQueryWrapper);
            receiveUserId = comment1.getUserId();
        }

        MessageModel<UserInfoDto> receiveUserInfoDtoMessageModel =
                userFeignClient.queryUserInfos(receiveUserId);

        MessageModel<UserInfoDto> SendUserInfoDtoMessageModel =
                userFeignClient.queryUserInfos(comment.getUserId());

        return CommentBo.builder()
                .comment(comment)
                .sendUserInfo(SendUserInfoDtoMessageModel.getData())
                .receiveUserInfo(receiveUserInfoDtoMessageModel.getData())
                .build();
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
