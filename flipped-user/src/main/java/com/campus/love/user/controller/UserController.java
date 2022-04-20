package com.campus.love.user.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }

    @PostMapping("/login")
    public MessageModel login(@RequestParam("code") String code) {
        //先获取openpid，再判断pid是否已经注册，若是，则直接登录，否则需要注册
        String openPid = userService.login(code);
        //获取pid失败
        if ("error".equals(openPid)) {
            return MessageModel.failed();
        }
        else {
            //判断是否已经注册
            User user = userService.getOneByPid(openPid);
            //未注册
            if (user == null) {
                // to be continued
                return MessageModel.success(user.getId());
            }
            //已注册，直接登录
            else {
                return MessageModel.success("login",user.getId());
            }
        }
    }

    @PostMapping("/logout")
    public MessageModel<Object> logout(@RequestParam("userId") Integer  userId){

        userService.logout(userId);
        return MessageModel.success();
    }

    @PostMapping("")
    public MessageModel<User> addOneUser(@RequestBody User user){
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

    @PostMapping("/user/avatar/userId/{id}")
    public MessageModel<String> alterAvatar(@PathVariable Integer id,
                                                          @RequestPart("file") MultipartFile file) {

        return MessageModel.success(userService.updateAvatar(id,file));
    }
}
