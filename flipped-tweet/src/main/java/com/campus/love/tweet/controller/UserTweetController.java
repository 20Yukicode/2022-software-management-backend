package com.campus.love.tweet.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.AddLikesVo;
import com.campus.love.tweet.domain.vo.PostTweetVo;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.tweet.service.LikesService;
import com.campus.love.tweet.service.UserTweetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户动态模块")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserTweetController {

    private final UserTweetService userTweetService;

    private final CommentService<CommentBo> commentService;

    private final LikesService likesService;

    public UserTweetController(UserTweetService userTweetService, CommentService<CommentBo> commentService, LikesService likesService) {
        this.userTweetService = userTweetService;
        this.commentService = commentService;
        this.likesService = likesService;
    }

    @ApiOperation("查询用户的所有动态")
    @GetMapping("/tweet/userId/{userId}")
    public MessageModel<List<Tweet>> queryUserTweets(@PathVariable Integer userId) {

        return MessageModel.success(userTweetService.getTweets(userId));
    }


    @ApiOperation("发布动态")
    @PostMapping("/tweet/publish")
    public MessageModel<Object> publishTweet(PostTweetVo postTweetVo) {
        log.info(postTweetVo.toString());
        Integer userId = postTweetVo.getUserId();

        AssertUtil.ifNull(userId, "用户Id不能为空");
        userTweetService.addTweet(postTweetVo);
        return MessageModel.success();
    }

    @ApiOperation("修改动态")
    @PutMapping("/tweet")
    public MessageModel<Object> modifyTweet(PostTweetVo tweet) {
        AssertUtil.validateNull(tweet.getTweetId(), "动态Id不难为空");
        userTweetService.changeTweet(tweet);

        return MessageModel.success();
    }

    @ApiOperation("移除动态")
    @DeleteMapping("/tweet/tweetId/{tweetId}")
    public MessageModel<Object> removeTweet(@PathVariable Integer tweetId){

        userTweetService.deleteTweet(tweetId);
        return MessageModel.success();
    }

    @ApiOperation("回复评论")
    @PostMapping("/comment")
    public MessageModel<Object> addComment(@RequestBody AddCommentVo addVo) {
        commentService.insertComment(addVo);
        return MessageModel.success();
    }


    @ApiOperation("删除回复")
    @DeleteMapping("/comment")
    public MessageModel<Object> removeComment(@RequestBody Operator operator) {

        commentService.deleteComment(operator);
        return MessageModel.success();
    }

    @ApiOperation("点赞")
    @PostMapping("/likes/like")
    public MessageModel<Object> likeTweetOrComment(@RequestBody AddLikesVo addLikesVo) {
        //todo 写一个判空工具类，递归读取属性
        likesService.likeTweetOrComment(addLikesVo);
        return MessageModel.success();
    }

    @ApiOperation("取消点赞")
    @PostMapping("/likes/unlike")
    public MessageModel<Object> unlikeTweetOrComment(@RequestBody AddLikesVo addLikesVo) {

        likesService.unlikeTweetOrComment(addLikesVo);
        return MessageModel.success();
    }



}
