package com.campus.love.tweet.service;

import com.campus.love.tweet.domain.vo.PostTweetVo;
import com.campus.love.tweet.entity.Tweet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserTweetService {

    List<Tweet> getTweets(Integer userId);

    void addTweet(PostTweetVo tweetVo);

    void changeTweet(PostTweetVo tweetVo);

    void deleteTweet(Integer tweetId);

}
