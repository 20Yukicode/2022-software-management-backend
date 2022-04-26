package com.campus.love.tweet.enums;

import lombok.Getter;

@Getter
public enum ReportedType {
    USER(0,"举报用户"),

    TWEET(1,"举报动态"),

    COMMENT(2,"举报评论");

    final Integer code;

    final String message;

    ReportedType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
