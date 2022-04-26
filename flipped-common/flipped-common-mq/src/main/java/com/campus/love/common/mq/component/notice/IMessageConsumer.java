package com.campus.love.common.mq.component.notice;

import com.campus.love.common.mq.domain.dto.NoticeDto;

public interface IMessageConsumer {

    void consumeMsg(NoticeDto message);
}
