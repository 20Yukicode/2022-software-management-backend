package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.util.SplitUtil;
import com.campus.love.tweet.domain.bo.CommentBo;
import com.campus.love.tweet.domain.bo.TweetBo;
import com.campus.love.tweet.enums.Order;
import com.campus.love.tweet.domain.vo.CommentTreeNodeVo;
import com.campus.love.tweet.domain.vo.TweetVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.manager.CommentManager;
import com.campus.love.tweet.manager.TweetManager;
import com.campus.love.tweet.service.TweetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetServiceImpl implements TweetService {

    private final TweetManager tweetManager;

    private final CommentManager commentManager;

    private final CommentServiceImpl1 commentService;

    public TweetServiceImpl(TweetManager tweetManager, CommentManager commentManager, CommentServiceImpl1 commentService) {
        this.tweetManager = tweetManager;
        this.commentManager = commentManager;
        this.commentService = commentService;
    }

    private <T> TweetVo<T> transferToVo(Tweet tweet, List<T> list) {
        TweetVo<T> tweetVo = new TweetVo<>();
        tweetVo.setComments(list);

        TweetBo build = TweetBo.builder()
                .tweetId(tweet.getId())
                .likeNum(tweet.getLikeNum())
                .commentNum(tweet.getCommentNum())
                .readNum(tweet.getReadNum())
                .content(tweet.getContent())
                .topic(tweet.getTopic())
                .userId(tweet.getUserId())
                .createTime(tweet.getCreateTime())
                .createTime(tweet.getUpdateTime())
                .urls(SplitUtil.split(tweet.getUrl(), String.class))
                .build();
        tweetVo.setTweetBo(build);

        return tweetVo;
    }

    @Override
    public TweetVo<CommentBo> getCommentsByTweet(Integer tweetId) {
        List<Comment> commentsByTweet = tweetManager.getCommentsByTweet(tweetId);
        Tweet tweet = tweetManager.getOneTweet(tweetId);
        //转换成commentBo
        List<CommentBo> collect = commentsByTweet
                .parallelStream()
                .map(commentManager::commentToBo)
                .collect(Collectors.toList());
        return transferToVo(tweet, collect);
    }


    @Override
    public TweetVo<CommentTreeNodeVo<CommentBo>> getAllCommentsByTweet(Integer tweetId, Order order) {
        List<Comment> commentsByTweet = tweetManager.getCommentsByTweet(tweetId);

        Tweet tweet = tweetManager.getOneTweet(tweetId);

        List<CommentTreeNodeVo<CommentBo>> collect = commentsByTweet
                .stream()
                .map(item -> commentService.GetAllComments(item.getId(), order))
                .collect(Collectors.toList());

//        //todo 这个list的每个元素只要前五个子评论
//        collect.parallelStream().forEach(item -> {
//            List<CommentBo> childCommentNodes = item.getChildCommentNodes();
//            //childCommentNodes只要前五个
//        });

        return transferToVo(tweet, collect);
    }
}
