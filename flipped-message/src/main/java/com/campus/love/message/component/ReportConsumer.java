package com.campus.love.message.component;

import com.campus.love.common.mq.component.notice.IReportConsumer;
import com.campus.love.common.mq.constant.ReportConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.message.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReportConsumer implements IReportConsumer {

    private final NoticeService noticeService;

    public ReportConsumer(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = ReportConstant.REPORT_EXCHANGE),
            value = @Queue(name = ReportConstant.REPORT_USER_QUEUE),
            key = ReportConstant.REPORT_USER_KEY
    ))
    @Override
    public void consumeReportUserMsg(NoticeDto message) {

        log.info("消费者接收到来自{},{}", ReportConstant.REPORT_USER_QUEUE, message.toString());
        noticeService.insertNotice(message);
    }



    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = ReportConstant.REPORT_EXCHANGE),
            value = @Queue(name = ReportConstant.REPORT_TWEET_QUEUE),
            key = ReportConstant.REPORT_TWEET_QUEUE
    ))
    @Override
    public void consumeReportTweetMsg(NoticeDto message) {

        log.info("消费者接收到来自{},{}", ReportConstant.REPORT_TWEET_QUEUE, message.toString());
        noticeService.insertNotice(message);
    }



    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = ReportConstant.REPORT_EXCHANGE),
            value = @Queue(name = ReportConstant.REPORT_COMMENT_QUEUE),
            key = ReportConstant.REPORT_COMMENT_KEY
    ))
    @Override
    public void consumeReportCommentMsg(NoticeDto message) {

        log.info("消费者接收到来自{},{}", ReportConstant.REPORT_COMMENT_QUEUE, message.toString());
        noticeService.insertNotice(message);
    }
}
