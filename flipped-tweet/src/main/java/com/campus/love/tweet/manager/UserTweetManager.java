package com.campus.love.tweet.manager;

import com.campus.love.common.core.util.FileUtil;
import com.campus.love.tweet.domain.vo.PostTweetVo;
import com.campus.love.tweet.entity.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserTweetManager {

    @Value("${prefix}")
    private String TWEET_PATH;

    private String generatorTweetPath(int userId,MultipartFile file) {
        LocalDate date = LocalDate.now();
        String path = "user/" + userId + "/tweet/" + date + "/" + file.getOriginalFilename();
        FileUtil.saveFile(path, file);
        return TWEET_PATH + "/"+path;
    }

    public Tweet postVo2tweet(PostTweetVo tweetVo) {
        Integer tweetId = tweetVo.getTweetId();
        Tweet.TweetBuilder builder = Tweet.builder();
        if (tweetVo.getReadNum() == null) {
            builder.readNum(0);
        }
        if (tweetVo.getCommentNum() == null) {
            builder.commentNum(0);
        }
        if (tweetVo.getLikeNum() == null) {
            builder.likeNum(0);
        }
        List<MultipartFile> files = tweetVo.getFiles();

        if (files != null && files.size() != 0) {
            String collect = files.stream()
                    .map(item -> generatorTweetPath(tweetVo.getUserId(), item))
                    .collect(Collectors.joining(","));
            builder.url(collect);
        }
        return builder
                .id(tweetId)
                .userId(tweetVo.getUserId())
                .content(tweetVo.getContent())
                .topic(tweetVo.getTopic())
                .build();

    }
}
