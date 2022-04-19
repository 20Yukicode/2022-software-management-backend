package com.campus.love.tweet.domain.vo;

import com.campus.love.tweet.domain.bo.TweetBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 评论下的第一层
 * @param <T>
 */
@Data
public class TweetVo<T> {

    @ApiModelProperty("动态实体")
    private TweetBo tweetBo;
    /**
     * 所有评论
     * 按照某条件排序
     */
    @ApiModelProperty("评论")
    private List<T> comments;

}
