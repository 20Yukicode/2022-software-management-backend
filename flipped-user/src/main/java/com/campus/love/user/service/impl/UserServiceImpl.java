package com.campus.love.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.LoginUtil;
import com.campus.love.user.entity.User;
import com.campus.love.user.mapper.SubscribedMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("login")
    public MessageModel login(String code) {
        MessageModel response = LoginUtil.getPluginOpenPId(code);
        //若成功获取openPid，判断为登陆成功
        if (response.getCode() == ResultCode.SUCCESS.getCode()) {
            return response;
        }
        //否则登陆失败
        else {
            return response;
        }
//        JSONObject jsonObject = LoginUtil.getAuth(code);//包含openID与secret
//        JSONObject res = new JSONObject();
//        String openid = jsonObject.getString("openid");
//        res.put("openid", openid);
//        boolean newUser = userService.createUserByOpenId(openid);
//        res.put("newuser", newUser);
//        return MessageModel.success();
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

    @Override
    public User getOneByPid(Integer pid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openPid",pid);
        return userMapper.selectOne(wrapper);
    }
}
