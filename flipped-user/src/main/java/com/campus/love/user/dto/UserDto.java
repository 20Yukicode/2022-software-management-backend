package com.campus.love.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class UserDto {
//    userInfo:
//    avatarUrl: "https://thirdwx.qlogo.cn/mmopen/vi_32/NuQfrY5jGS5Tb0lJGJzIHq9aOJ8ZHBhibz5Cm98r9iccEEctEaDeWpqDUeVneUvKxURmr7usKfz588icm5RjS819g/132"
//    city: ""
//    country: ""
//    gender: 0
//    language: "zh_CN"
//    nickName: "蒋汶霖"
//    province: ""
    /**
     * 用户昵称
     */
    @NotNull
    private String nickName;

    /**
     * 头像Url
     */
    @NotNull
    private String avatarUrl;

    /**
     * 用户身份唯一标识符
     */
    @NotNull
    private String openPid;


}
