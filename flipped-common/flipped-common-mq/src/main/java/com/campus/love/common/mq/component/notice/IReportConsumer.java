package com.campus.love.common.mq.component.notice;

import com.campus.love.common.mq.domain.dto.NoticeDto;

public interface IReportConsumer {

    void consumeReportUserMsg(NoticeDto message);

    void consumeReportTweetMsg(NoticeDto message);

    void consumeReportCommentMsg(NoticeDto message);
}
