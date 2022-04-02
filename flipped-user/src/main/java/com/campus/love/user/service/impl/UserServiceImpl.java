package com.campus.love.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.user.entity.Subscribed;
import com.campus.love.user.entity.User;
import com.campus.love.user.mapper.SubscribedMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final SubscribedMapper subscribedMapper;

    public UserServiceImpl(UserMapper userMapper, SubscribedMapper subscribedMapper) {
        this.userMapper = userMapper;
        this.subscribedMapper = subscribedMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User getOneById(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
        }
        return user;
    }

    @Override
    public List<String> getSubscribedById(Integer id) {
        return userMapper.getSubscribedById(id);
    }
}
