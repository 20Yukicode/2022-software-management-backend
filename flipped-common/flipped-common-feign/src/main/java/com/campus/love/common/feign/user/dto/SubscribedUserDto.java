package com.campus.love.common.feign.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SubscribedUserDto {

    @ApiModelProperty("用户Id")
    private int userId;

    @ApiModelProperty("用户名称")
    private String name;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户关注时间(距离现在的时间)")
    private Date createTime;

    @ApiModelProperty("回关")
    private boolean isFollow;
}
