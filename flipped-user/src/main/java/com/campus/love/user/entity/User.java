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
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User extends BaseEntity {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别

     */
    private String gender;

    /**
     * 0-单身
1-恋爱
     */
    private Integer state;

    /**
     * 现住地

     */
    private String address;

    /**
     * 家乡
     */
    private String hometown;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 学历
     */
    private String degree;

    /**
     * 专业
     */
    private String major;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Integer weight;

    /**
     * 个人描述
     */
    private String description;

    /**
     * 黑名单
     */
    private String blacklist;

    /**
     * 相册（数组）
     */
    private String photoAlbum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}