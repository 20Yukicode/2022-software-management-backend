package com.campus.love.common.mq.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LikesDto {

    @ApiModelProperty("点赞Id")
    private Integer likesId;

    @ApiModelProperty("点赞数")
    private Integer num;
}
