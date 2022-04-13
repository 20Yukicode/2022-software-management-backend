package com.campus.love.common.feign.module.tweet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户评论
 * 默认按时间排序
 */
@Data
public class CommentDto {

    @ApiModelProperty("评论用户Id")
    private Integer commentUserId;

    @ApiModelProperty("评论用户名字")
    private Integer name;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("用户评论时间(距离现在的时间)")
    private Date createTime;

    @ApiModelProperty("相关动态页面Url")
    private String url;
}
