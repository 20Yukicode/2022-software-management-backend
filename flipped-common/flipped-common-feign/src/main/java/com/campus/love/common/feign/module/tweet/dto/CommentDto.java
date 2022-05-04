package com.campus.love.common.feign.module.tweet.dto;

import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户评论
 * 默认按时间排序
 */
@Data
public class CommentDto {

    @ApiModelProperty("用户信息")
    private UserInfoDto userInfoDto;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户评论时间")
    private Date createTime;

    @ApiModelProperty("相关动态id")
    private Integer tweetId;

    //如果这个为空，那么肯定是对动态的回复
    @ApiModelProperty("相关评论Id")
    private Integer commentId;

    @ApiModelProperty("是否已读")
    private Integer readState;

}
