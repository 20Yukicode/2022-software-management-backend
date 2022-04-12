package com.campus.love.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.campus.love.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName notice
 */
@TableName(value ="notice")
@Data
public class Notice extends BaseEntity {
    /**
     * Id
     */
    @TableId
    @ApiModelProperty("通知Id")
    private Integer id;

    /**
     * 消息Id
     */
    @ApiModelProperty("消息Id")
    private Integer messageId;

    /**
     * 接收消息用户Id
     */
    @ApiModelProperty("接收通知用户Id")
    private Integer userId;

    /**
     * 0-关注，1-点赞，2-评论，3-系统通知
     * 通知类型，查看是从哪个数据库来的
     */
    @ApiModelProperty("通知类型")
    private Integer messageType;

    /**
     * 是否被阅读（0-读过，1-没读过）
     */
    @ApiModelProperty("消息是否读过")
    private Integer isRead;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}