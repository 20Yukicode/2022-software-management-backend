package com.campus.love.tweet.service;


import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.domain.vo.AddLikesVo;

public interface LikesService {

    void likeTweetOrComment(AddLikesVo addLikesVo);

    void unlikeTweetOrComment(AddLikesVo addLikesVo);

    LikesDto getLikesDetail(Integer likesId);


}
