package com.campus.love.tweet.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCommentVo extends AddVo{

    @NotNull
    @ApiModelProperty("评论内容")
    private String content;
}
