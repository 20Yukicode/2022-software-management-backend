package com.campus.love.tweet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.campus.love.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName topic
 */
@TableName(value ="topic")
@Data
public class Topic extends BaseEntity {
    /**
     * 话题id
     */
    @ApiModelProperty("话题Id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String heading;

    /**
     * 参与人数
     */
    @ApiModelProperty("参与人数")
    private Integer participants;

    /**
     * 动态数
     */
    @ApiModelProperty("动态数")
    private Integer tweetNum;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}