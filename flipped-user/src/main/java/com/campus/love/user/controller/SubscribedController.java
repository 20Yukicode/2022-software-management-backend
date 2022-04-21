package com.campus.love.user.controller;


import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.Subscribed;
import com.campus.love.user.service.SubscribedService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class SubscribedController {

    private final SubscribedService subscribedService;

    public SubscribedController(SubscribedService subscribedService) {
        this.subscribedService = subscribedService;
    }

    @PostMapping("/subscribe")
    public MessageModel<Subscribed> addSubscribed(@RequestBody Subscribed subscribed){
        //判断是否已经关注
        Subscribed _subscribed = subscribedService.getSubscribed(subscribed.getFirstUserId()
                ,subscribed.getSecondUserId());
        //未关注
        if (_subscribed == null){
            _subscribed = subscribedService.insertSubscribed(subscribed);
            return _subscribed != null ? MessageModel.success("关注成功",_subscribed)
                    : MessageModel.failed("关注失败");
        }
        //已关注
        return MessageModel.failed("重复关注");

    }

    @DeleteMapping("/subscribe")
    public MessageModel removeSubscribed(@RequestParam("firstUserId") Integer firstId,
                                                     @RequestParam("secondUserId")Integer seconId){
        return subscribedService.deleteSubscribed(firstId,seconId) == 0 ? MessageModel.failed("取关失败")
                : MessageModel.success("取关成功");
    }
}
