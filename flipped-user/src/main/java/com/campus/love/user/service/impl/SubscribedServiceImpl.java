package com.campus.love.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.user.entity.Subscribed;
import com.campus.love.user.entity.User;
import com.campus.love.user.mapper.SubscribedMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.SubscribedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribedServiceImpl implements SubscribedService {

    private final UserMapper userMapper;

    private final SubscribedMapper subscribedMapper;

    public SubscribedServiceImpl(UserMapper userMapper, SubscribedMapper subscribedMapper) {
        this.userMapper = userMapper;
        this.subscribedMapper = subscribedMapper;
    }

    @Override
    public Subscribed insertSubscribed(Subscribed subscribed) {
        int result = subscribedMapper.insert(subscribed);
        return result > 0 ? subscribed : null;
    }

    @Override
    public Integer deleteSubscribed(int firstUserid, int secondUserid) {
        QueryWrapper<Subscribed> wrapper = new QueryWrapper<>();
        wrapper.eq("first_user_id",firstUserid).eq("second_user_id",secondUserid);
        return subscribedMapper.delete(wrapper);
    }

    @Override
    public Subscribed getSubscribed(int firstUserid, int secondUserid) {
        QueryWrapper<Subscribed> wrapper = new QueryWrapper<>();
        wrapper.eq("first_user_id",firstUserid).eq("second_user_id",secondUserid);
        return subscribedMapper.selectOne(wrapper);
    }

    @Override
    public List<Subscribed> getSubscribedList(int userId) {

        User user = userMapper.selectById(userId);
        if (user == null) {
            AssertUtil.failed("用户不存在");
            return null;
        }
        QueryWrapper<Subscribed> wrapper = new QueryWrapper<>();
        wrapper.eq("first_user_id",userId);
        return subscribedMapper.selectList(wrapper);
    }

}
