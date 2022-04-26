package com.campus.love.tweet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.campus.love.common.core.domain.BaseEntity;
import com.google.common.io.BaseEncoding;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName report
 */
@TableName(value ="report")
@Data
@Builder
public class Report extends BaseEntity {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 举报内容
     */
    private String content;

    /**
     * 违规类型
     */
    private String violateType;

    /**
     * 举报ID
     */
    private Integer reportedId;

    /**
     * 被举报内容的类型(0-user,1-comment,2-tweet)
     */
    private Integer reportedType;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}