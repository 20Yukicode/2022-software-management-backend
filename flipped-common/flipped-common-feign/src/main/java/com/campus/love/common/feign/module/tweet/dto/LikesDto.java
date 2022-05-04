package com.campus.love.common.feign.module.tweet.dto;

import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户点赞
 * 默认按时间降序排列
 */
@Data
public class LikesDto {

    @ApiModelProperty("用户信息")
    private UserInfoDto userInfoDto;

    @ApiModelProperty("用户点赞时间")
    private Date createTime;

    @NotNull
    @ApiModelProperty("相关动态的Id")
    private Integer tweetId;

    //如果这个为空，那么一定是对动态的点赞
    @ApiModelProperty("相关评论的Id")
    private Integer commentId;

    @ApiModelProperty("是否已读")
    private Integer readState;

}
