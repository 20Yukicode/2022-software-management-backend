package com.campus.love.tweet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.FileUtil;
import com.campus.love.tweet.domain.vo.PostTweetVo;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.manage.UserTweetManage;
import com.campus.love.tweet.mapper.TweetMapper;
import com.campus.love.tweet.service.UserTweetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTweetServiceImpl implements UserTweetService {

    private final TweetMapper tweetMapper;

    private final UserTweetManage userTweetManage;

    public UserTweetServiceImpl(TweetMapper tweetMapper, UserTweetManage userTweetManage) {
        this.tweetMapper = tweetMapper;
        this.userTweetManage = userTweetManage;
    }

    @Override
    public List<Tweet> getTweets(Integer userId) {
        LambdaQueryWrapper<Tweet> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Tweet::getUserId,userId);
        return tweetMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void addTweet(PostTweetVo tweetVo) {
        Tweet tweet = userTweetManage.postVo2tweet(tweetVo);
        int insert = tweetMapper.insert(tweet);
        if (insert == 0) {
            AssertUtil.failed("插入动态失败");
        }
        tweet.setUrl(FileUtil.saveTweets(tweet.getId(), tweetVo.getFiles()));
        int i = tweetMapper.updateById(tweet);
        if(i==0){
            AssertUtil.failed("插入动态失败(插入图片)");
        }
    }

    @Override
    public void changeTweet(PostTweetVo tweetVo) {

        Tweet tweet = userTweetManage.postVo2tweet(tweetVo);
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
