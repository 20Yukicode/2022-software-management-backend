package com.campus.love.demo.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.SynchronizedByKey;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
import com.campus.love.common.mq.enums.MessageType;
import com.campus.love.demo.domain.enums.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private static final SynchronizedByKey<Integer> synchronizedByKey=new SynchronizedByKey<>();

    private static final SynchronizedByKey<Integer> synchronizedByKey1=new SynchronizedByKey<>();


    private final RabbitTemplate rabbitTemplate;

    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @SentinelResource("sentinel")
    @GetMapping("/sentinel")
    public MessageModel<Object> sentinel(){

        return null;
    }

    @RequestMapping(value = "/mq", method = RequestMethod.GET)
    public MessageModel<String> mq() {

        NoticeMqDto noticeMqDto = NoticeMqDto.builder()
                .messageId(3)
                .userId(1)
                .messageType(MessageType.COMMENT_COMMENT).build();
        String exchange = TweetConstant.TWEET_EXCHANGE;
        rabbitTemplate.convertAndSend(exchange, TweetConstant.COMMENT_KEY, noticeMqDto);

        return MessageModel.success("hello world");
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1() {
        System.out.println("begin");
       new Thread(()->{
            try {
                Thread.sleep(10000);
                BufferedWriter writer=
                        new BufferedWriter(new FileWriter("D:\\360MoveData\\Users\\86180\\Desktop\\a.txt"));

                writer.write("kotlin");
                writer.close();
                System.out.println(233);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();


        System.out.println("end");
        return MessageModel.success("end");
    }

    @RequestMapping(value = "/enum", method = RequestMethod.GET)
    public MessageModel<String> helloWorld1(@RequestParam("order")Order order) {

        log.info(order.toString());
        return MessageModel.success("hello world");
    }


    @GetMapping("/syn")
    public void testSyn(@RequestParam("userId") Integer userId){

//        log.info("开始"+userId);
//        try{
//            Thread.sleep(1000);
//        }catch (Exception ignored){
//
//        }
//        log.info("结束"+userId);

        System.out.println(synchronizedByKey);
        synchronizedByKey.exec(userId,()->{
            log.info("开始1");
            try{
                Thread.sleep(2000);
            }catch (Exception ignored){

            }
            log.info("结束1");
        });
        System.out.println(synchronizedByKey1);
        synchronizedByKey1.exec(userId,()->{
            log.info("开始2");
            try{
                Thread.sleep(100);
            }catch (Exception ignored){

            }
            log.info("结束2");
        });
    }

    @GetMapping("/syn1")
    public void testSyn1(@RequestParam("userId") Integer userId){

//        log.info("开始"+userId);
//        try{
//            Thread.sleep(1000);
//        }catch (Exception ignored){
//
//        }
//        log.info("结束"+userId);

        System.out.println(synchronizedByKey);
        synchronizedByKey.exec(userId,()->{
            log.info("开始1");
            try{
                Thread.sleep(2000);
            }catch (Exception ignored){

            }
            log.info("结束1");
        });
        System.out.println(synchronizedByKey1);
        synchronizedByKey1.exec(userId,()->{
            log.info("开始2");
            try{
                Thread.sleep(100);
            }catch (Exception ignored){

            }
            log.info("结束2");
        });
    }
}
