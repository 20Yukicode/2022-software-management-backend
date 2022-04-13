package com.campus.love.common.feign.module.tweet;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.domain.FeignConstant;
import com.campus.love.common.feign.module.tweet.dto.CommentDto;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 在我未读的所有动态
 */
@FeignClient(value = "flipped-tweet",contextId = "tweet")
public interface TweetFeignClient {

    @ApiOperation("获取点赞用户列表")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX+"/likes/{userId}")
    MessageModel<List<LikesDto>> queryLikes(@PathVariable Integer userId);


    @ApiOperation("获取评论用户列表")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX+"/comment/{userId}")
    MessageModel<List<CommentDto>> queryComments(@PathVariable Integer userId);

}
