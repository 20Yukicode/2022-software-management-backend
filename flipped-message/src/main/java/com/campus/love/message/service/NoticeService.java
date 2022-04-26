package com.campus.love.message.service;

import com.campus.love.common.mq.domain.dto.NoticeDto;

public interface NoticeService {

     void insertNotice(NoticeDto noticeDto);

     void setNoticeRead(Integer noticeId);

}
