package com.campus.love.message.service.notice;

import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;

import java.util.List;

public interface SubscribedService {


    List<SubscribedUserDto> getSubscribedList(Integer userId);
}
