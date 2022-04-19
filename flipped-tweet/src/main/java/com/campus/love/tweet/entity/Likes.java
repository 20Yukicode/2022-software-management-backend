package com.campus.love.tweet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.campus.love.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName likes
 */
@TableName(value ="likes")
@Data
@Builder
public class Likes extends BaseEntity {
    /**
     * 点赞id
     */
    @ApiModelProperty("点赞Id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 来自于评论或者动态的Id
     */
    @ApiModelProperty("评论或者动态Id")
    private Integer likedId;

    /**
     * 点赞者
     */
    @ApiModelProperty("点赞者Id")
    private Integer userId;

    /**
     * 是否是动态1-tweet 0-comment
     */
    @ApiModelProperty("是否是动态")
    private Integer isTweet;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}