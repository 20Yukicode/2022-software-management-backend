package com.campus.love.user.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }

    @PostMapping("/login")
    public MessageModel<String> login(@RequestParam("userId") Integer userId,
                                    @RequestParam("password")String password) {
        String login = userService.login(userId, password);
        return MessageModel.success(login);
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
}
