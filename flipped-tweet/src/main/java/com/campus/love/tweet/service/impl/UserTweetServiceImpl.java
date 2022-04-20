package com.campus.love.tweet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.mapper.TweetMapper;
import com.campus.love.tweet.service.UserTweetService;
import com.campus.love.tweet.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserTweetServiceImpl implements UserTweetService {

    private final TweetMapper tweetMapper;

    public UserTweetServiceImpl(TweetMapper tweetMapper) {
        this.tweetMapper = tweetMapper;
    }

    @Override
    public List<Tweet> getTweets(Integer userId) {
        LambdaQueryWrapper<Tweet> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Tweet::getUserId,userId);
        return tweetMapper.selectList(queryWrapper);
    }

    @Override
    public Integer addTweet(List<MultipartFile> files, String topic, String content, Integer userId) {
        String urls = FileUtil.getUrls(files);
        Tweet tweet = Tweet.builder()
                .url(urls)
                .topic(topic)
                .content(content)
                .userId(userId)
                .content(content).build();
        int insert = tweetMapper.insert(tweet);
        if(insert==0){
            AssertUtil.failed("插入动态失败");
        }
        return tweet.getId();
    }

    @Override
    public void changeTweet(Tweet tweet) {
        int i = tweetMapper.updateById(tweet);
        if(i==0){
            AssertUtil.failed("动态Id为"+tweet.getId()+"不存在");
        }
    }

    @Override
    public void deleteTweet(Integer tweetId) {
        if(tweetMapper.deleteById(tweetId)==0){
            AssertUtil.failed("动态Id为"+tweetId+"不存在");
        }
    }


}
