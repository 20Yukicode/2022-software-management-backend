package com.campus.love.user.service;

import com.campus.love.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    String login(String code);

    void logout(Integer userId);

    User insertUser(User user);

    List<User> getAllUsers();

    User getOneById(Integer id);

    List<String> getSubscribedById(Integer id);

    User getOneByPid(String pid);

    String updateAvatar(Integer id, MultipartFile file);

    int updateUserInfo(User user);

    List<String> getAlbum(Integer id);

    List<String> insertAlbum(Integer id, List<MultipartFile> files);

    List<String> deleteAlbum(Integer id, Integer nums);



}
