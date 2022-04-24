package com.campus.love.common.core.util.dto;

import lombok.Data;

@Data
public class SessionDto {
    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionId;

    /**
     * 错误码
     */
    private Integer number;

    /**
     * 错误信息
     */
    private String errMsg;

    @Override
    public String toString(){
        return "{openId: " + openId + "\nsession_key: " + session_key + "\nunionId: " + unionId + "\nnumber: "
                + number + "\nerrMsg:" + errMsg + "}";
    }

}
