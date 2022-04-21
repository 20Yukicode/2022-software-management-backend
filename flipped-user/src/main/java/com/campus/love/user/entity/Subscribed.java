package com.campus.love.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.campus.love.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName subscribed
 */
@TableName(value ="subscribed")
@Data
public class Subscribed  extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关注者A
     */
    private Integer firstUserId;

    /**
     * 关注者B
     */
    private Integer secondUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}