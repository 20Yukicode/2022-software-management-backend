package com.campus.love.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.campus.love.common.core.api.MessageModel;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.engines.CamelliaWrapEngine;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private static final Map<Integer,String> userInfos=new HashMap<>();

    @Data
    private static class UserInfo{
        Integer id;

        String password;
    }

    static {
        userInfos.put(10010,"nzh");
        userInfos.put(10020,"nyw");
    }

    @PostMapping("")
    public MessageModel<Object> login(@RequestBody UserInfo userInfo){

        log.info(userInfo.toString());
        if(userInfo.password.equals(userInfos.get(userInfo.id))) {
            StpUtil.login(10010);
            return MessageModel.success();
        }

        return MessageModel.unauthorized();
    }


    @GetMapping("")
    public String test(){

        Object loginId = StpUtil.getLoginId();
        System.out.println(loginId);
        StpUtil.isLogin();
        return "success";
    }
}
