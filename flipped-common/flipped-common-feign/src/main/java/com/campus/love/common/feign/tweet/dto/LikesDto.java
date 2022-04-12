package com.campus.love.common.feign.tweet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户点赞
 * 默认按时间降序排列
 */
@Data
public class LikesDto {

    @ApiModelProperty("点赞用户Id")
    private Integer likesUserId;

    @ApiModelProperty("最新一个点赞的用户名称")
    private String name;

    @ApiModelProperty("最新一个点赞用户头像")
    private String avatar;

    @ApiModelProperty("用户关注时间(距离现在的时间)")
    private Date createTime;

    @ApiModelProperty("其他点赞用户的头像")
    private List<String> otherAvatars;

    @ApiModelProperty("相关动态页面Url")
    private String url;

}
