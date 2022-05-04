package com.campus.love.common.mq.component.notice;

import com.campus.love.common.mq.domain.dto.NoticeMqDto;

public interface IMessageConsumer {

    void consumeMsg(NoticeMqDto message);
}
