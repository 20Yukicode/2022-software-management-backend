package com.campus.love.tweet.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class TweetBo {

    @ApiModelProperty("动态Id")
    private Integer tweetId;

    @ApiModelProperty("喜欢数")
    private Integer likeNum;

    @ApiModelProperty("阅读数")
    private Integer readNum;

    @ApiModelProperty("评论数")
    private Integer commentNum;

    @ApiModelProperty("话题")
    private String topic;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("动态创建时间")
    private Date createTime;

    @ApiModelProperty("动态更新时间")
    private Date updateTime;

    @ApiModelProperty("图像/视频")
    private List<String> urls;

}
