package com.campus.love.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.api.ResultCode;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.FileUtil;
import com.campus.love.common.core.util.LoginUtil;
import com.campus.love.user.entity.User;
import com.campus.love.user.mapper.SubscribedMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_PATH = "user";

    private static final String AVATAR_PATH = "avatar";

    private static final String ALBUM_PATH = "album";

    private static final String BASE_PATH = "https://flipped-bucket.oss-cn-shanghai.aliyuncs.com";

    private final UserMapper userMapper;

    private final SubscribedMapper subscribedMapper;

    public UserServiceImpl(UserMapper userMapper, SubscribedMapper subscribedMapper) {
        this.userMapper = userMapper;
        this.subscribedMapper = subscribedMapper;
    }

    @Override
    @GetMapping("login")
    public String login(String code) {
        return LoginUtil.getPluginOpenPId(code);
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
    public User getOneByPid(String pid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("openPid",pid);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 更新用户头像（涉及删除旧头像 + 添加新头像）
     * @param id 用户唯一身份标识符
     * @param file 头像文件
     */
    @Override
    public String updateAvatar(Integer id, MultipartFile file) {
        //查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
            return "error";
        }

        //删除旧头像
        String oldAvatar = user.getAvatar();
        if (!StringUtils.isEmpty(oldAvatar)) {
            //除去域名前缀
            String[] value = oldAvatar.split(BASE_PATH+"/");
            FileUtil.deleteFile(value[1]);
        }
        String filePath = USER_PATH + "/" + id + "/" + AVATAR_PATH + "/" + file.getOriginalFilename();

        //添加新头像（oss）
        String fileUrl = FileUtil.saveFile(file,filePath);
        //添加新头像（mysql）
        QueryWrapper updateWrapper = new QueryWrapper();
        updateWrapper.eq("id",id);
        user.setAvatar(fileUrl);
        userMapper.update(user,updateWrapper);


        return fileUrl;
    }
}
