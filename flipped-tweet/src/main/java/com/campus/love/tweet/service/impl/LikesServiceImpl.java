package com.campus.love.tweet.service.impl;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.tweet.dto.LikesDto;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.common.mq.constant.TweetConstant;
import com.campus.love.common.mq.domain.dto.NoticeMqDto;
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

    private final UserFeignClient userFeignClient;

    public LikesServiceImpl(LikesMapper likesMapper, SynchronizedByKey<Operator> synchronizedByKey, RabbitTemplate rabbitTemplate, LikesManager likesManager, UserFeignClient userFeignClient) {
        this.likesMapper = likesMapper;
        this.synchronizedByKey = synchronizedByKey;
        this.rabbitTemplate = rabbitTemplate;
        this.likesManager = likesManager;
        this.userFeignClient = userFeignClient;
    }


    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void likeComment(AddLikesVo addLikesVo) {
        Operator operator = addLikesVo.getOperator();
        Integer tweetId = operator.getTweetId();
        Integer commentId = operator.getCommentId();
        OperatorType operatorType = operator.getOperatorType();


        Likes.LikesBuilder builder = Likes.builder();
        switch (operatorType) {
            case TWEET:
                builder.tweetId(tweetId);
                builder.commentId(null);
                break;
            case COMMENT:
                builder.tweetId(tweetId);
                builder.commentId(commentId);
                break;
            default:
                throw new ApiException("不存在其他类型的点赞");
        }
        Likes build = builder
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
            NoticeMqDto noticeMqDto = likesManager.generatorNoticeDto(build);
            rabbitTemplate.convertAndSend(exchange, key, noticeMqDto);

            likesManager.changeLikesNum(tweetId,commentId, operatorType, ChangeType.ADD);
        });
    }

    @Transactional(rollbackFor = ApiException.class)
    @Override
    public void unlikeComment(Integer likeId, Operator operator) {
        Integer tweetId = operator.getTweetId();
        Integer commentId = operator.getCommentId();
        OperatorType operatorType = operator.getOperatorType();

        synchronizedByKey.exec(operator, () -> {
            int i = likesMapper.deleteById(likeId);
            AssertUtil.failed(() -> i == 0, "取消点赞失败");
            likesManager.changeLikesNum(tweetId, commentId, operatorType, ChangeType.SUB);
        });
    }

    @Override
    public LikesDto getLikesDetail(Integer likesId) {
        Likes likes = likesMapper.selectById(likesId);
        AssertUtil.ifNull(likes, "找不到Id为" + likesId + "的点赞消息");

        Integer userId = likes.getUserId();
        MessageModel<UserInfoDto> userInfoDtoMessageModel = userFeignClient.queryUserInfos(userId);
        UserInfoDto data = userInfoDtoMessageModel.getData();

        LikesDto likesDto = new LikesDto();
        likesDto.setUserInfoDto(data);
        likesDto.setTweetId(likes.getTweetId());
        likesDto.setCommentId(likes.getCommentId());
        likesDto.setCreateTime(likes.getCreateTime());
        return likesDto;
    }

}
