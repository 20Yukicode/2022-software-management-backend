package com.campus.love.common.mq.component;

import com.campus.love.common.mq.domain.dto.NoticeDto;

public interface IMessageConsumer {

    void receiveCommentMessage(NoticeDto message);

    void receiveLikesMessage(NoticeDto message);

    void receiveSubscribedMessage(NoticeDto message);
}
