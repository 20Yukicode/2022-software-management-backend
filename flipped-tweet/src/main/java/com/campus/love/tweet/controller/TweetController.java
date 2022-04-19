package com.campus.love.tweet.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.enums.Order;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.domain.vo.TweetVo;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.tweet.service.TweetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    private final CommentService<CommentBo> commentService;


    public TweetController(TweetService tweetService, CommentService<CommentBo> commentService) {
        this.tweetService = tweetService;
        this.commentService = commentService;
    }

    @ApiOperation("展示一条动态及下面的所有评论(只有直接)")
    @GetMapping("/{tweetId}")
    public MessageModel<TweetVo<CommentBo>> queryDirectComments(@PathVariable Integer tweetId) {

        TweetVo<CommentBo> tweetAndComments = tweetService.getCommentsByTweet(tweetId);

        return MessageModel.success(tweetAndComments);

    }

    @ApiOperation("展示一条动态及下面的所有评论及下面的五条评论(可以间接)")
    @GetMapping("/{tweetId}/all")
    public MessageModel<TweetVo<CommentTreeNodeVo<CommentBo>>>
    queryInDirectCommentsByTweet(@PathVariable Integer tweetId,
                                 @RequestParam(value = "order",required = false) Order order) {
        TweetVo<CommentTreeNodeVo<CommentBo>> commentTreeNodeVoTweetVo =
                tweetService.getAllCommentsByTweet(tweetId, order);
        return MessageModel.success(commentTreeNodeVoTweetVo);
    }


    @ApiOperation("展示一条评论以及下面的所有评论(可以间接)")
    @GetMapping("/{commentId}/all")
    public MessageModel<CommentTreeNodeVo<CommentBo>>
    queryInDirectCommentsByComment(@PathVariable Integer commentId,
                                   @RequestParam(value = "order",required = false) Order order) {
        order = order == null ? Order.TIME_DESC : order;
        CommentTreeNodeVo<CommentBo> comments =
                commentService.GetAllComments(commentId, order);
        return MessageModel.success(comments);
    }
}
