package com.campus.love.common.mq.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    FOCUS_ON(0, "关注", "Subscribed"),

    GIVE_A_LIKE(1, "点赞", "Likes"),

    COMMENT(2, "评论", "Comment"),

    SYSTEM_NOTICE(3, "系统通知", "Notice");

    final Integer number;

    final String description;

    final String tableName;

    MessageType(Integer number, String description, String tableName) {
        this.number = number;
        this.description = description;
        this.tableName = tableName;
    }

}
