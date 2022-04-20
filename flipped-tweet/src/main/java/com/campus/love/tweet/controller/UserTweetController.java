package com.campus.love.tweet.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.vo.AddCommentVo;
import com.campus.love.tweet.domain.vo.AddLikesVo;
import com.campus.love.tweet.domain.vo.PublishTweetVo;
import com.campus.love.tweet.entity.Likes;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.tweet.service.LikeService;
import com.campus.love.tweet.service.UserTweetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserTweetController {

    private final UserTweetService userTweetService;

    private final CommentService<CommentBo> commentService;

    private final LikeService likeService;

    public UserTweetController(UserTweetService userTweetService, CommentService<CommentBo> commentService, LikeService likeService) {
        this.userTweetService = userTweetService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @ApiOperation("查询一个用户所有的动态")
    @GetMapping("/tweet/{userId}")
    public MessageModel<List<Tweet>> queryUserTweets(@PathVariable Integer userId) {

        return MessageModel.success(userTweetService.getTweets(userId));
    }


    @ApiOperation("发布动态")
    @PostMapping("/tweet/publish")
    public MessageModel<Integer> publishTweet(@RequestBody PublishTweetVo publishTweetVo){

        List<MultipartFile> files = publishTweetVo.getFiles();
        String topic = publishTweetVo.getTopic();
        String content = publishTweetVo.getContent();
        Integer userId = publishTweetVo.getUserId();

        AssertUtil.ifNull(userId,"用户Id不能为空");

        return MessageModel.success(userTweetService.addTweet(files, topic, content, userId));
    }

    @ApiOperation("修改动态")
    @PutMapping("/tweet")
    public MessageModel<Tweet> modifyTweet(@RequestBody Tweet tweet) {

        userTweetService.changeTweet(tweet);

        return MessageModel.success(tweet);
    }

    @ApiOperation("移除动态")
    @DeleteMapping("/tweet/{tweetId}")
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


    @ApiOperation("移除回复")
    @PostMapping("/comment/{commentId}")
    public MessageModel<Object> removeComment(@PathVariable Integer commentId){

        commentService.deleteComment(commentId);
        return MessageModel.success();
    }

    @ApiOperation("点赞")
    @PostMapping("/likes")
    public MessageModel<Object> likeAComment(@RequestBody AddLikesVo addLikesVo) {

        likeService.likeOrUnlikeComment(addLikesVo);
        return MessageModel.success();
    }


}
