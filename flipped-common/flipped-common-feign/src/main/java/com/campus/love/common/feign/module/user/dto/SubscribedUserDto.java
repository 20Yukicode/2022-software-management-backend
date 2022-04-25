package com.campus.love.common.feign.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SubscribedUserDto {

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户关注时间(距离现在的时间)")
    private Date createTime;

    @ApiModelProperty("是否回关")
    private Boolean isFollow;
}
