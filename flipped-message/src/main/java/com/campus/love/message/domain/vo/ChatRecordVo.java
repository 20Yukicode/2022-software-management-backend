package com.campus.love.message.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatRecordVo {

    @NotNull
    private Integer userAId;

    @NotNull
    private Integer userBId;

    private Integer pageNum;

    private Integer pageSize;
}
