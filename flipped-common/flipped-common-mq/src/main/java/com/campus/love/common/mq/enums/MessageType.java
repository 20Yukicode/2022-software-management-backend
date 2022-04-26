package com.campus.love.common.mq.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    FOCUS_ON(0, "关注", "Subscribed"),

    LIKES_TWEET(1, "点赞动态", "Likes"),

    LIKES_COMMENT(2,"点赞动态","Likes"),

    COMMENT_TWEET(3,"评论动态","Comment"),
    COMMENT_COMMENT(4, "评论评论", "Comment"),

    SYSTEM_NOTICE(5, "系统通知", "Notice"),

    REPORT_USER(6,"举报用户","Report"),

    REPORT_TWEET(7,"举报动态","Report"),

    REPORT_COMMENT(8,"举报评论","Report");

    final Integer number;

    final String description;

    final String tableName;

    MessageType(Integer number, String description, String tableName) {
        this.number = number;
        this.description = description;
        this.tableName = tableName;
    }

}
