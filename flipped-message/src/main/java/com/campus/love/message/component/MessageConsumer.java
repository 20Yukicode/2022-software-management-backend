package com.campus.love.message.component;

import com.campus.love.common.mq.component.IMessageConsumer;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer implements IMessageConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = TweetConstant.COMMENT_QUEUE),
            exchange = @Exchange(name = TweetConstant.TWEET_EXCHANGE),
            key = TweetConstant.COMMENT_KEY
    ))
    @Override
    public void receiveCommentMessage(NoticeDto message) {
        log.info("消费者接收到" + message.toString());
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = TweetConstant.LIKES_QUEUE),
            exchange = @Exchange(name = TweetConstant.TWEET_EXCHANGE),
            key = TweetConstant.LIKES_KEY
    ))
    @Override
    public void receiveLikesMessage(NoticeDto message) {
        log.info("消费者接收到" + message.toString());
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = TweetConstant.SUBSCRIBED_QUEUE),
            exchange = @Exchange(name = TweetConstant.TWEET_EXCHANGE),
            key = TweetConstant.SUBSCRIBED_KEY
    ))
    @Override
    public void receiveSubscribedMessage(NoticeDto message) {
        log.info("消费者接收到" + message.toString());
    }
}
