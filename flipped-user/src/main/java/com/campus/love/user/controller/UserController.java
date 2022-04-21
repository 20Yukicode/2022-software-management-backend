package com.campus.love.user.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.UserService;
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

    @PostMapping("/login")
    public MessageModel login(@RequestParam("code") String code) {
        try {
            //先获取openpid，再判断pid是否已经注册，若是，则直接登录，否则需要注册
            String openPid = userService.login(code);
            //判断是否已经注册
            User user = userService.getOneByPid(openPid);
            //未注册
            if (user == null) {
                // to be continued
                return MessageModel.success("用户未注册", user.getId());
            }
            //已注册，判断登陆状态
            else if(user.getLoginState() == 0) {
                return MessageModel.success("用户未登录", user.getId());
            }
            else {
                return MessageModel.success("用户已登录", user.getId());
            }
        } catch (ApiException e) {
            return MessageModel.failed(e.getMessage());
        }

    }

    @PostMapping("/logout")
    public MessageModel<Object> logout(@RequestParam("userId") Integer  userId){
        userService.logout(userId);
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

    @GetMapping("/subscribed/userId/{id}")
    public MessageModel<List<String>> querySubscribedById(@PathVariable Integer id) {

        return MessageModel.success(userService.getSubscribedById(id));
    }

    @PostMapping("/avatar/userId/{id}")
    public MessageModel<String> alterAvatar(@PathVariable Integer id,
                                                          @RequestPart("file") MultipartFile file) {

        return MessageModel.success(userService.updateAvatar(id,file));
    }

    @PostMapping("/userInfo")
    public MessageModel<Integer> alterUserInfo(@RequestBody User user) {

        return MessageModel.success(userService.updateUserInfo(user));
    }
}
