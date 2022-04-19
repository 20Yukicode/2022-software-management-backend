package com.campus.love.tweet.service;


import com.campus.love.tweet.domain.vo.AddLikesVo;

public interface LikeService {

    void likeOrUnlikeComment(AddLikesVo addLikesVo);


}
