package com.campus.love.common.feign.tweet;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.domain.FeignConstant;
import com.campus.love.common.feign.tweet.dto.CommentDto;
import com.campus.love.common.feign.tweet.dto.LikesDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 在我未读的所有动态
 */
@FeignClient(value = "flipped-tweet",contextId = "tweet")
public interface TweetFeignClient {

    @ApiOperation("获取点赞用户列表")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX+"/likes")
    MessageModel<List<LikesDto>> queryLikes(Integer userId);


    @ApiOperation("获取评论用户列表")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX+"/comment")
    MessageModel<List<CommentDto>> queryComments(Integer userId);

}
