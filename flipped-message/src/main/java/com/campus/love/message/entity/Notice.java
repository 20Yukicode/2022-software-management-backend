package com.campus.love.message.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.campus.love.common.core.domain.BaseEntity;
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
    private Integer id;

    /**
     * 消息Id
     */
    private Integer messageId;

    /**
     * 接收消息用户Id
     */
    private Integer userId;

    /**
     * 0-关注，1-点赞，2-评论，3-系统通知
     */
    private Integer messageType;

    /**
     * 是否被阅读（0-读过，1-没读过）
     */
    private Integer isRead;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}