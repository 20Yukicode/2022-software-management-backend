package com.campus.love.tweet.domain.bo;

import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.tweet.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 第二层评论的Bo
 * 第一层是动态的直接子节点
 * 第二层是
 */
@Data
@Builder
public class CommentBo {

    @ApiModelProperty("回复的用户")
    private UserInfoDto sendUserInfo;

    @ApiModelProperty("收到回复的用户")
    private UserInfoDto receiveUserInfo;

    @ApiModelProperty("回复的评论")
    private Comment comment;
}
