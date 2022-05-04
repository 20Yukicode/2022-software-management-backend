package com.campus.love.common.mq.domain.dto;

import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.common.mq.enums.ReadState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class NoticeMqDto implements Serializable {

    @ApiModelProperty("消息Id")
    private Integer messageId;

    @ApiModelProperty("接收通知用户Id")
    private Integer userId;

    @ApiModelProperty("通知类型")
    private MessageType messageType;

    @ApiModelProperty("是否被阅读")
    private ReadState readState;

    @ApiModelProperty("通知创建时间")
    private Date createTime;

}
