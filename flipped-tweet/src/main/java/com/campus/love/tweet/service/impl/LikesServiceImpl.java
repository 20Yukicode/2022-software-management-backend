package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeDto;
import com.campus.love.tweet.domain.constant.ChangeType;
import com.campus.love.tweet.enums.Operator;
import com.campus.love.tweet.enums.OperatorType;
import com.campus.love.tweet.domain.vo.AddLikesVo;
import com.campus.love.tweet.entity.Likes;
import com.campus.love.tweet.manager.LikesManager;
import com.campus.love.tweet.mapper.LikesMapper;
import com.campus.love.tweet.service.LikesService;
import com.campus.love.common.core.util.SynchronizedByKey;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesMapper likesMapper;

    private final SynchronizedByKey<Operator> synchronizedByKey;

    private final RabbitTemplate rabbitTemplate;

    private final LikesManager likesManager;

    public LikesServiceImpl(LikesMapper likesMapper, SynchronizedByKey<Operator> synchronizedByKey, RabbitTemplate rabbitTemplate, LikesManager likesManager) {
        this.likesMapper = likesMapper;
        this.synchronizedByKey = synchronizedByKey;
        this.rabbitTemplate = rabbitTemplate;
        this.likesManager = likesManager;
    }


    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void likeComment(AddLikesVo addLikesVo) {
        Operator operator = addLikesVo.getOperator();
        Integer operatorId = operator.getTweetOrCommentId();
        OperatorType operatorType = operator.getOperatorType();

        Likes build = Likes.builder()
                .likedId(operatorId)
                .isTweet(operatorType.getCode())
                .userId(addLikesVo.getUserId())
                .build();

        //这里要加锁,并且是对operatorId，operatorType相同的情况下加锁
        //考虑拓展性，后面可能会加redis
        synchronizedByKey.exec(operator, () -> {
            int insert = likesMapper.insert(build);
            AssertUtil.failed(() -> insert == 0, "点赞失败");

            //发送到消息队列
            String exchange = TweetConstant.TWEET_EXCHANGE;
            String key = TweetConstant.LIKES_KEY;
            NoticeDto noticeDto = likesManager.generatorNoticeDto(build);
            rabbitTemplate.convertAndSend(exchange, key, noticeDto);

            likesManager.changeLikesNum(operatorId, operatorType, ChangeType.ADD);
        });
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void unlikeComment(Integer likeId, Operator operator) {
        Integer operatorId = operator.getTweetOrCommentId();
        OperatorType operatorType = operator.getOperatorType();

        synchronizedByKey.exec(operator, () -> {
            int i = likesMapper.deleteById(likeId);
            AssertUtil.failed(() -> i == 0, "取消点赞失败");
            likesManager.changeLikesNum(operatorId, operatorType, ChangeType.SUB);
        });
    }

}
