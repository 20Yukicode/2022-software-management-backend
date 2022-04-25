package com.campus.love.tweet.enums;

public enum Order {
    TIME_DESC(1, "按照时间降序"),

    TIME_ASC(2, "按照时间升序"),

    LIKES_DESC(3,"按照点赞数降序"),

    LIKES_ASC(4,"按照点赞数升序"),

    COMMENT_DESC(5,"按照评论数降序"),

    COMMENT_ASC(6,"按照评论数升序");


    final long code;

    final String message;

    Order(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
