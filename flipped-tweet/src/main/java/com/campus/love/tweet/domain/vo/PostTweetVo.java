package com.campus.love.tweet.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostTweetVo {

    @ApiModelProperty("动态Id")
    private Integer tweetId;

    @ApiModelProperty("话题")
    private String topic;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("动态的图像,音频,链接")
    private List<MultipartFile> files;

    @ApiModelProperty("阅读数")
    private Integer readNum;

    @ApiModelProperty("评论数")
    private Integer commentNum;

    @ApiModelProperty("喜欢数")
    private Integer likeNum;
}
