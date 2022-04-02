package com.campus.love.user.entity;

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
 * @TableName label
 */
@TableName(value ="label")
@Data
public class Label extends BaseEntity {
    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 性格
     */
    private String character;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 小众文化
     */
    private String minority;

    /**
     * 自定义
     */
    private String userDefined;

    /**
     * 用户id
     */
    private Integer userId;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}