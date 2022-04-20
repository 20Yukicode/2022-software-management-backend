package com.campus.love.tweet.service;

import com.campus.love.tweet.entity.Tweet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserTweetService {

    List<Tweet> getTweets(Integer userId);

    Integer addTweet(List<MultipartFile> files, String topic, String content, Integer userId);

    void changeTweet(Tweet tweet);

    void deleteTweet(Integer tweetId);

}
