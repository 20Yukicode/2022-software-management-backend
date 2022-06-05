package com.campus.love.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.druid.util.StringUtils;
import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.user.dto.UserDto;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.UserService;
import io.swagger.models.auth.In;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }

    @PostMapping("/register")
    public MessageModel register(@RequestBody @Validated UserDto userDto){
        if (StringUtils.isEmpty(userDto.getOpenPid())) {
            return MessageModel.failed("openPid错误");
        }
        User user = userService.getOneByPid(userDto.getOpenPid());
        //用户未注册
        if (user == null) {
            user = new User();
            user.setAvatar(userDto.getAvatarUrl());
            user.setNickname(StringUtils.isEmpty(userDto.getNickName()) ? "" : userDto.getNickName());
            user.setOpenPid(userDto.getOpenPid());
            user.setLoginState(1);
            userService.insertUser(user);
            if (user.getId() > 0) {
                StpUtil.login(user.getId());
                return MessageModel.success("注册成功",user);
            }
            else {
                return MessageModel.failed("注册失败");
            }
        }
        //用户已注册
        else {
            //更改登陆状态
            user.setLoginState(1);
            userService.updateUserInfo(user);
            StpUtil.login(user.getId());
            return MessageModel.success("成功登录", user);
        }

    }

    @GetMapping("/login")
    public MessageModel login(@RequestParam("code") String code) {
        try {
            //先获取openPid，再判断pid是否已经注册，若是，则直接登录，否则需要注册
            String openPid = userService.login(code);

            //判断是否已经注册
            User user = userService.getOneByPid(openPid);

            if (StringUtils.isEmpty(openPid)) {
                return MessageModel.failed("无效code");
            }
            //未注册
            if (user == null) {
                // to be continued
                return MessageModel.success("用户未注册", openPid);
            }
            //已注册，判断登陆状态
            else if(user.getLoginState() == 0) {

                return MessageModel.success("用户未登录", user.getId());
            } else {
                StpUtil.login(user.getId());
                return MessageModel.success("用户已登录", user.getId());
            }
        } catch (ApiException e) {
            return MessageModel.failed(e.getMessage());
        }

    }

    @PostMapping("/logout")
    public MessageModel<Object> logout(@RequestParam("userId") Integer  userId){
        userService.logout(userId);
        StpUtil.logout(userId);
        return MessageModel.success();
    }

    @PostMapping("")
    public MessageModel<User> addOneUser(@RequestBody User user){
        //用户注册
        User insertUser = userService.insertUser(user);
        return MessageModel.success(insertUser);
    }

    @GetMapping("/all")
    public MessageModel<List<User>> queryAllUsers(){

        return MessageModel.success(userService.getAllUsers());
    }

    @GetMapping("/userId/{id}")
    public MessageModel<User> queryById(@PathVariable Integer id){

        return MessageModel.success(userService.getOneById(id));
    }

    @GetMapping("/userId/{id}/subscribed")
    public MessageModel<List<String>> querySubscribedById(@PathVariable Integer id) {

        return MessageModel.success(userService.getSubscribedById(id));
    }

    @PostMapping("/userId/{id}/avatar")
    public MessageModel<String> alterAvatar(@PathVariable Integer id,
                                                          @RequestPart("file") MultipartFile file) {

        return MessageModel.success(userService.updateAvatar(id,file));
    }

    @PostMapping("/userInfo")
    public MessageModel<Integer> alterUserInfo(@RequestBody User user) {

        return MessageModel.success(userService.updateUserInfo(user));
    }

    @GetMapping("/userId/{id}/album")
    public MessageModel<List<String>> queryAlbum(@PathVariable Integer id) {

        List<String> fileUrlList = userService.getAlbum(id);
        return fileUrlList == null ? MessageModel.failed("获取失败") : MessageModel.success(fileUrlList);
    }

    @PostMapping("/userId/{id}/album")
    public MessageModel<List<String>> addAlum(@PathVariable Integer id,
                                            @RequestPart("files") List<MultipartFile> files) {

        List<String> fileUrlList = userService.insertAlbum(id, files);
        return fileUrlList == null ? MessageModel.failed("插入失败") : MessageModel.success("插入成功",fileUrlList);
    }

    @DeleteMapping("/userId/{id}/album")
    public MessageModel removeAlum(@PathVariable Integer id,
                                              @RequestParam("nums") Integer nums) {

        List<String> fileUrlList = userService.deleteAlbum(id, nums);
        return fileUrlList == null ? MessageModel.failed("删除失败")
                : MessageModel.success("删除成功",fileUrlList);
    }
}
