package com.campus.love.common.mq.component.notice;

import com.campus.love.common.mq.domain.dto.NoticeMqDto;

public interface IReportConsumer {

    void consumeReportUserMsg(NoticeMqDto message);

    void consumeReportTweetMsg(NoticeMqDto message);

    void consumeReportCommentMsg(NoticeMqDto message);
}
