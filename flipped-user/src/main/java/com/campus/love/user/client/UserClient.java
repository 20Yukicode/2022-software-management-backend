package com.campus.love.user.client;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.domain.FeignConstant;
import com.campus.love.common.feign.module.user.UserFeignClient;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import com.campus.love.user.entity.Subscribed;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.SubscribedService;
import com.campus.love.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class UserClient implements UserFeignClient {

    private final UserService userService;

    private final SubscribedService subscribedService;

    public UserClient(UserService userService, SubscribedService subscribedService) {
        this.userService = userService;
        this.subscribedService = subscribedService;
    }

    @Override
    //@GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX + "/subscribed")
    public MessageModel<List<SubscribedUserDto>> querySubscribedInfo(@RequestParam("userId") Integer userId) {
        List<Subscribed> subscribedList = subscribedService.getSubscribedList(userId);
        List<SubscribedUserDto> userList = subscribedList.stream()
                .map(item -> {
                    User subscribedUser = userService.getOneById(item.getSecondUserId());
                    return SubscribedUserDto.builder().userId(subscribedUser.getId())
                            .avatar(subscribedUser.getAvatar())
                            .name(subscribedUser.getNickname())
                            .createTime(subscribedUser.getCreateTime())
                            .isFollow(subscribedService.getSubscribed(subscribedUser.getId()
                                    , userId) == null ? false : true)
                                .build();
                }).collect(Collectors.toList());
        return MessageModel.success(userList);
    }

    @Override
    public MessageModel<UserInfoDto> queryUserInfos(@RequestParam("userId") Integer userId) {
        User user = userService.getOneById(userId);
        if (user != null) {
            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .userId(user.getId())
                    .userName(user.getNickname())
                    .userAvatar(user.getAvatar())
                    .build();
            return MessageModel.success(userInfoDto);
        }
        return MessageModel.failed("用户不存在");
    }

    @Override
    public MessageModel<List<Integer>> queryAccordingToLabel(Integer userId) {
        return null;
    }
}
