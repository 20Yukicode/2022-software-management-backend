package com.campus.love.tweet.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Operator {

    @NotNull
    @ApiModelProperty("动态Id")
    private Integer tweetId;

    @ApiModelProperty("评论Id")
    private Integer commentId;

    @NotNull
    @ApiModelProperty("回复类型")
    private OperatorType operatorType;
}
