package com.campus.love.user.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.User;
import com.campus.love.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userServiceImpl;

    public UserController(UserService userService){
        this.userServiceImpl=userService;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public MessageModel<List<User>> queryAllUsers(){

        return MessageModel.success(userServiceImpl.getAllUsers());
    }
}
