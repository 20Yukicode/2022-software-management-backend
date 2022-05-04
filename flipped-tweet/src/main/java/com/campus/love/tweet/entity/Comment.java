package com.campus.love.tweet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.campus.love.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
@Builder
public class Comment extends BaseEntity {
    /**
     * 评论id
     */
    @ApiModelProperty("评论Id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("评论属于哪个动态")
    private Integer tweetId;
    /**
     * 父节点是动态
     */
    @ApiModelProperty("父节点是动态")
    private Integer pTweetId;

    /**
     * 父节点是评论
     */
    @ApiModelProperty("父节点是评论")
    private Integer pCommentId;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("点赞数")
    private Integer likesNum;

    /**
     * 评论数
     */
    @ApiModelProperty("评论数")
    private Integer commentNum;

    /**
     * 用户id
     */
    @ApiModelProperty("用户Id")
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}