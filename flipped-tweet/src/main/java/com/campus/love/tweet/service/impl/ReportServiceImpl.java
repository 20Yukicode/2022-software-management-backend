package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.constant.ReportConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.common.mq.enums.ReadState;
import com.campus.love.tweet.domain.vo.ReportVo;
import com.campus.love.tweet.entity.Comment;
import com.campus.love.tweet.entity.Report;
import com.campus.love.tweet.entity.Tweet;
import com.campus.love.tweet.enums.ReportedType;
import com.campus.love.tweet.mapper.CommentMapper;
import com.campus.love.tweet.mapper.ReportMapper;
import com.campus.love.tweet.mapper.TweetMapper;
import com.campus.love.tweet.service.ReportService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    private final TweetMapper tweetMapper;

    private final CommentMapper commentMapper;

    private final RabbitTemplate rabbitTemplate;


    public ReportServiceImpl(ReportMapper reportMapper, TweetMapper tweetMapper, CommentMapper commentMapper, RabbitTemplate rabbitTemplate) {
        this.reportMapper = reportMapper;
        this.tweetMapper = tweetMapper;
        this.commentMapper = commentMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void insertReport(ReportVo reportVo) {
        ReportedType reportedType = reportVo.getReportedType();
        Report report = Report.builder()
                .reportedId(reportVo.getReportedObjectId())
                .reportedType(reportedType.getCode())
                .content(reportVo.getContent())
                .violateType(reportVo.getViolateType()).build();

        int insert = reportMapper.insert(report);
        AssertUtil.failed(() -> insert == 0, "插入report表失败");

        Integer reportedId = report.getReportedId();
        String exchange = ReportConstant.REPORT_EXCHANGE;
        String key = null;
        NoticeDto.NoticeDtoBuilder builder = NoticeDto.builder();
        switch (reportedType) {
            case USER:
                key = ReportConstant.REPORT_USER_KEY;

                NoticeDto noticeDto = builder
                        .readState(ReadState.NOT_READ)
                        .messageId(report.getId())
                        .messageType(MessageType.REPORT_USER)
                        //对于user来说，被举报的id就是userId
                        .userId(reportedId)
                        .createTime(report.getCreateTime())
                        .build();
                rabbitTemplate.convertAndSend(exchange, key, noticeDto);

            case TWEET:
                key = ReportConstant.REPORT_TWEET_KEY;
                Tweet tweet = tweetMapper.selectById(reportedId);
                AssertUtil.ifNull(tweet, "不存在该tweet");

                NoticeDto noticeDto1 = builder
                        .readState(ReadState.NOT_READ)
                        .messageId(report.getId())
                        .messageType(MessageType.REPORT_TWEET)
                        //对tweet来说，我们需要找到tweet属于哪个user
                        .userId(tweet.getUserId())
                        .createTime(report.getCreateTime())
                        .build();
                rabbitTemplate.convertAndSend(exchange, key, noticeDto1);

            case COMMENT:
                key = ReportConstant.REPORT_COMMENT_KEY;
                Comment comment = commentMapper.selectById(reportedId);
                AssertUtil.ifNull(comment, "不存在该comment");

                NoticeDto noticeDto2 = builder
                        .readState(ReadState.NOT_READ)
                        .messageId(report.getId())
                        .messageType(MessageType.REPORT_COMMENT)
                        //对comment来说我们需要找到comment属于哪个user
                        .userId(comment.getUserId())
                        .createTime(report.getCreateTime())
                        .build();
                rabbitTemplate.convertAndSend(exchange, key, noticeDto2);

            default:
                AssertUtil.failed("举报type不存在");
        }
    }
}
