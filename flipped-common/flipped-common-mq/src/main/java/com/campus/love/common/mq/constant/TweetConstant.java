package com.campus.love.common.mq.constant;

public interface TweetConstant {

    String TWEET_EXCHANGE = "flipped.tweet.exchange";

    String LIKES_QUEUE="flipped.tweet.likes.queue";

    String LIKES_KEY="likes";

    String COMMENT_QUEUE="flipped.tweet.comment.queue";

    String COMMENT_KEY="comment";


    String SUBSCRIBED_QUEUE="flipped.tweet.subscribed.queue";

    String SUBSCRIBED_KEY="subscribed";
}
