package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.vo.AddLikesVo;
import com.campus.love.tweet.entity.Likes;
import com.campus.love.tweet.mapper.LikesMapper;
import com.campus.love.tweet.service.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesMapper likesMapper;

    public LikeServiceImpl(LikesMapper likesMapper) {
        this.likesMapper = likesMapper;
    }

    @Override
    public void likeOrUnlikeComment(AddLikesVo addLikesVo) {
        Likes build = Likes.builder()
                .likedId(addLikesVo.getOperator().getOperatorId())
                .isTweet(addLikesVo.getOperator().getOperatorType().getCode())
                .userId(addLikesVo.getUserId())
                .build();

        int insert = likesMapper.insert(build);
        AssertUtil.failed(()->insert==0,"点赞失败");
    }
}
