package com.campus.love.common.feign.module.user;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.feign.domain.FeignConstant;
import com.campus.love.common.feign.module.user.dto.SubscribedUserDto;
import com.campus.love.common.feign.module.user.dto.UserInfoDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("flipped-user")
public interface UserFeignClient {

    @ApiOperation("获取关注者的信息")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX + "/subscribed")
    MessageModel<List<SubscribedUserDto>> querySubscribedInfo(Integer userId);


    @ApiOperation("获取某用户的名字，头像")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX + "/userInfo")
    MessageModel<UserInfoDto> queryUserInfos(Integer userId);


    /**
     * 这个还没想好
     * @param userId
     * @return
     */
    @ApiOperation("根据用户标签来筛选动态")
    @GetMapping(FeignConstant.FEIGN_INSIDE_URL_PREFIX + "/{userId}")
    MessageModel<List<Integer>> queryAccordingToLabel(@PathVariable Integer userId);

}
