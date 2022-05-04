package com.campus.love.message.component;

import com.campus.love.common.mq.component.notice.IMessageConsumer;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
import com.campus.love.message.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommentConsumer implements IMessageConsumer {

    private final NoticeService noticeService;

    public CommentConsumer(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = TweetConstant.TWEET_EXCHANGE),
            value = @Queue(name = TweetConstant.COMMENT_QUEUE),
            key = TweetConstant.COMMENT_KEY
    ))
    @Override
    public void consumeMsg(NoticeMqDto message) {
        log.info("消费者接收到" + message.toString());
        noticeService.insertNotice(message);
    }
}
