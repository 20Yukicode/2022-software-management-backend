package com.campus.love.tweet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.campus.love.common.core.domain.BaseEntity;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName tweet
 */
@TableName(value ="tweet")
@Data
@Builder
public class Tweet extends BaseEntity {
    /**
     * 动态id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 喜欢数
     */
    private Integer likeNum;

    /**
     * 阅读数
     */
    private Integer readNum;

    /**
     * 图像/语音/视频
     */
    private String url;

    /**
     * 话题
     */
    private String topic;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 用户id
     */
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}