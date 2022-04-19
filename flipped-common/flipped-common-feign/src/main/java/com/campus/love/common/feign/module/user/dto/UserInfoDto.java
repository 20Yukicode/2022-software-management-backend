package com.campus.love.common.feign.module.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoDto {

    @ApiModelProperty("用户Id")
    private Integer userId;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("用户头像")
    private String userAvatar;
}
