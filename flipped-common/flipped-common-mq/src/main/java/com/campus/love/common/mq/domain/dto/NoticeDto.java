package com.campus.love.common.mq.domain.dto;

import com.campus.love.common.mq.enums.MessageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NoticeDto implements Serializable {

    @ApiModelProperty("消息Id")
    private Integer messageId;

    @ApiModelProperty("接收通知用户Id")
    private Integer userId;

    @ApiModelProperty("通知类型")
    private MessageType messageType;

}
