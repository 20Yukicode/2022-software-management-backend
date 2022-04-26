package com.campus.love.common.mq.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentDto {

    @ApiModelProperty("评论Id")
    private Integer commentId;


    @ApiModelProperty("评论数")
    private Integer num;
}
