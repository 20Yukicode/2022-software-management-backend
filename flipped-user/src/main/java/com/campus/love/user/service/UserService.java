package com.campus.love.user.service;

import com.campus.love.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAllUsers();

    User getOneById(Integer id);

    List<String> getSubscribedById(Integer id);


}
