package com.campus.love.tweet.enums;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.tweet.domain.bo.CommentBo;
import lombok.Getter;

import java.util.Comparator;

@Getter
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

    public static Comparator<? super CommentBo> strategy(Order order) {
        switch (order) {
            case TIME_DESC:
                return (a, b) -> a.getComment().getCreateTime().after(b.getComment().getCreateTime()) ? 1 : 0;
            case TIME_ASC:
                return (a, b) -> a.getComment().getCreateTime().before(b.getComment().getCreateTime()) ? 1 : 0;
            case LIKES_ASC:
                return (a,b)->1;
            case COMMENT_ASC:
                return (a,b)->1;
            case LIKES_DESC:
                return (a,b)->1;
            case COMMENT_DESC:
                return (a,b)->1;
            default:
                throw new ApiException("不存在这种排序类型");
        }
    }
}
