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
 * @TableName criteria
 */
@TableName(value ="criteria")
@Data
public class Criteria extends BaseEntity {
    /**
     * 用户id
     */
    @TableId
    private Integer id;

    /**
     * 年龄范围
     */
    private String ageRange;

    /**
     * 期望身高
     */
    private String statureRange;

    /**
     * 期望城市(数组）
     */
    private String cityRange;

    /**
     * 期望家乡(数组）
     */
    private String hometownRange;

    /**
     * 对象性别（0-异性，1-同性，2-都可以）
     */
    private Integer gender;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}