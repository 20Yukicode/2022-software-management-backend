package com.campus.love.tweet.service;


import com.campus.love.tweet.domain.enums.Operator;
import com.campus.love.tweet.domain.vo.AddLikesVo;

public interface LikesService {

    void likeComment(AddLikesVo addLikesVo);

    void unlikeComment(Integer likeId, Operator operator);


}
