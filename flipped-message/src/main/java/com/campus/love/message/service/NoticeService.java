package com.campus.love.message.service;

import com.campus.love.common.feign.module.tweet.dto.CommentDto;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;

import java.util.List;

public interface NoticeService {


     void insertNotice(NoticeMqDto noticeMqDto);

     void setNoticeRead(Integer noticeId);

     List<CommentDto> getCommentList(Integer userId);

     List<LikesDto> getLikesList(Integer userId);


}
