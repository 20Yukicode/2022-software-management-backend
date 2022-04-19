package com.campus.love.tweet.domain.enums;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Operator {

    @NotNull
    @ApiModelProperty("动态Id或评论Id")
    private Integer operatorId;

    @NotNull
    @ApiModelProperty("回复类型")
    private OperatorType operatorType;
}
