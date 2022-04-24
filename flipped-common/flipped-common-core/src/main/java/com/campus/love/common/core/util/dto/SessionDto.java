package com.campus.love.common.core.util.dto;

import lombok.Data;

@Data
public class SessionDto {
    /**
     * 用户唯一标识
     */
    private String openid;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionid;

    /**
     * 错误码
     */
    private Integer number;

    /**
     * 错误信息
     */
    private String errmsg;

    @Override
    public String toString(){
        return "{openId: " + openid + "\nsession_key: " + session_key + "\nunionId: " + unionid + "\nnumber: "
                + number + "\nerrMsg:" + errmsg + "}";
    }

}
