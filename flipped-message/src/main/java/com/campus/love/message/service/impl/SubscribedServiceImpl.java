package com.campus.love.message.service.impl;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.message.service.SubscribedService;
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

        return subscribedInfo.getData();
    }
}
