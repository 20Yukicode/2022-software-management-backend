package com.campus.love.tweet.client;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.tweet.TweetFeignClient;
import com.campus.love.common.feign.module.tweet.dto.CommentDto;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.service.CommentService;
import com.campus.love.tweet.service.LikesService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TweetClient implements TweetFeignClient {

    private final LikesService likesService;

    private final CommentService<CommentBo> commentService;

    public TweetClient(LikesService likesService, CommentService<CommentBo> commentService) {
        this.likesService = likesService;
        this.commentService = commentService;
    }


    @Override
    public MessageModel<LikesDto> queryLikes(Integer messageId) {

        AssertUtil.validateNull(messageId,"消息Id不能为空");

        LikesDto likesDetail = likesService.getLikesDetail(messageId);

        return MessageModel.success(likesDetail);
    }

    @Override
    public MessageModel<CommentDto> queryComments(Integer messageId) {

        AssertUtil.validateNull(messageId, "消息Id不能为空");

        CommentDto commentDetail = commentService.getCommentDetail(messageId);

        return MessageModel.success(commentDetail);
    }
}
