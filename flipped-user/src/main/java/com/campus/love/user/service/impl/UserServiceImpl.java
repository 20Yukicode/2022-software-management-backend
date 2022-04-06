package com.campus.love.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.util.AssertUtil;
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
    public String login(Integer userId, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, userId);
        queryWrapper.eq(User::getPassword, password);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            AssertUtil.failed("用户名或密码错误");
        }
        StpUtil.login(userId);
        return StpUtil.getTokenInfo().tokenValue;
    }

    @Override
    public void logout(Integer userId) {
        StpUtil.logout(userId);
    }

    @Override
    public User insertUser(User user) {
        int insert = userMapper.insert(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        IPage<User>page=new Page<>();
        IPage<User> iPage = userMapper.selectPage(page, null);
        iPage.
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
