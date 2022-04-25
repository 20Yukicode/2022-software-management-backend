package com.campus.love.tweet.enums;

import lombok.Getter;

@Getter
public enum OperatorType {

    COMMENT(0, "评论"),

    TWEET(1, "动态");

    final Integer code;

    final String message;

    OperatorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
