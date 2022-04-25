package com.campus.love.message.domain.bo;

import com.campus.love.message.entity.ChatRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 两个人所有聊天记录
 */
@Builder
@Data
public class ChatRecordBo {

    @ApiModelProperty("用户AId")
    private Integer userAId;

    @ApiModelProperty("用户BId")
    private Integer userBId;

    @ApiModelProperty("用户B的名称")
    private String userBName;

    @ApiModelProperty("用户B头像")
    private String userBAvatar;

    @ApiModelProperty("用户之间聊天记录")
    private List<ChatRecord> userChatRecordBoList;
}
