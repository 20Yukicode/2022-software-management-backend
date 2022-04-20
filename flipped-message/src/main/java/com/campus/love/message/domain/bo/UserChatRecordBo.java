package com.campus.love.message.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 两个人的一条聊天记录
 */
@Data
@Builder
public class UserChatRecordBo {

    @ApiModelProperty("聊天记录Id")
    private Integer chatRecordId;

    @ApiModelProperty("聊天内容")
    private String content;

    @ApiModelProperty("消息是否已读")
    private Integer isRead;

    @ApiModelProperty("消息创建时间")
    private Date createTime;

    @ApiModelProperty("消息更新时间")
    private Date updateTime;

}
