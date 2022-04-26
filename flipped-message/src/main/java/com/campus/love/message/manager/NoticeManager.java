package com.campus.love.message.manager;

import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.message.entity.Notice;
import com.campus.love.message.mapper.NoticeMapper;
import org.springframework.stereotype.Component;

@Component
public class NoticeManager {

    private final NoticeMapper noticeMapper;

    public NoticeManager(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }


}
