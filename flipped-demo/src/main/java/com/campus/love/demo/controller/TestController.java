package com.campus.love.demo.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.SynchronizedByKey;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.demo.domain.enums.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final SynchronizedByKey<Integer> synchronizedByKey;

    private final SynchronizedByKey<Integer> synchronizedByKey1;


    private final RabbitTemplate rabbitTemplate;

    public TestController(SynchronizedByKey<Integer> synchronizedByKey, SynchronizedByKey<Integer> synchronizedByKey1, RabbitTemplate rabbitTemplate) {
        this.synchronizedByKey = synchronizedByKey;
        this.synchronizedByKey1 = synchronizedByKey1;
        this.rabbitTemplate = rabbitTemplate;
    }


    @RequestMapping(value = "/mq", method = RequestMethod.GET)
    public MessageModel<String> mq() {

        NoticeDto noticeDto = NoticeDto.builder()
                .messageId(3)
                .userId(1)
                .messageType(MessageType.COMMENT_COMMENT).build();
        String exchange = TweetConstant.TWEET_EXCHANGE;
        rabbitTemplate.convertAndSend(exchange, TweetConstant.COMMENT_KEY, noticeDto);

        return MessageModel.success("hello world");
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1() {

        return MessageModel.success("hello world");
    }

    @RequestMapping(value = "/enum", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1(@RequestParam("order")Order order) {

        log.info(order.toString());
        return MessageModel.success("hello world");
    }


    @GetMapping("/syn")
    public void testSyn(@RequestParam("userId") Integer userId){

        log.info("开始"+userId);
        try{
            Thread.sleep(1000);
        }catch (Exception ignored){

        }
        log.info("结束"+userId);

//        synchronizedByKey.exec(userId,()->{
//            log.info("开始");
//            log.info(userId.toString());
//            try{
//                Thread.sleep(2000);
//            }catch (Exception ignored){
//
//            }
//            log.info("结束");
//        });
//        synchronizedByKey1.exec(userId,()->{
//            log.info("开始2");
//            try{
//                Thread.sleep(100);
//            }catch (Exception ignored){
//
//            }
//            log.info("结束2");
//        });
    }
}
