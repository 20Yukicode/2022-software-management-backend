package com.campus.love.message.service.notice.impl;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.message.service.notice.SubscribedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribedServiceImpl implements SubscribedService {

    private final UserFeignClient userFeignClient;

    public SubscribedServiceImpl(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public List<SubscribedUserDto> getSubscribedList(Integer userId) {
        MessageModel<List<SubscribedUserDto>> subscribedInfo =
                userFeignClient.querySubscribedInfo(userId);

        List<SubscribedUserDto> data = subscribedInfo.getData();

        if (data == null) {
            AssertUtil.failed("不存在该用户" + userId);
        }

        return data;
    }
}
