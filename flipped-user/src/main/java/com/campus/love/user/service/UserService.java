package com.campus.love.user.service;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    MessageModel login(String code);

    void logout(Integer userId);

    User insertUser(User user);

    List<User> getAllUsers();

    User getOneById(Integer id);

    List<String> getSubscribedById(Integer id);

    User getOneByPid(Integer pid);


}
