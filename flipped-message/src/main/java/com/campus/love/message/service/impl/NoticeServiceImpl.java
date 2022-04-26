package com.campus.love.message.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.message.entity.Notice;
import com.campus.love.message.mapper.NoticeMapper;
import com.campus.love.message.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }


    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void setNoticeRead(Integer noticeId) {
        Notice notice = noticeMapper.selectById(noticeId);
        AssertUtil.ifNull(notice, "找不到对应的notice");
        notice.setIsRead(ReadState.IS_READ.getNumber());
        int i = noticeMapper.updateById(notice);
        AssertUtil.failed(() -> i == 0, "更新notice表失败");
    }

    @Override
    public void insertNotice(NoticeDto noticeDto) {
        Notice build = Notice.builder()
                .isRead(noticeDto.getReadState().getNumber())
                .messageId(noticeDto.getMessageId())
                .messageType(noticeDto.getMessageType().getNumber())
                .userId(noticeDto.getUserId())
                .build();
        int insert = noticeMapper.insert(build);
        AssertUtil.failed(() -> insert == 0, "插入消息失败");
    }
}
