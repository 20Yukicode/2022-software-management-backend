package com.campus.love.user.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campus.love.common.core.exception.ApiException;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.common.core.util.FileUtil;
import com.campus.love.common.core.util.LoginUtil;
import com.campus.love.common.core.util.dto.SessionDto;
import com.campus.love.user.entity.User;
import com.campus.love.user.mapper.SubscribedMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public String login(String code) throws ApiException{
        SessionDto session = LoginUtil.code2Session(code);
        return session.getOpenid();

    }

    @Override
    public void logout(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
        }
        //设置登陆状态
        user.setLoginState(0);
        userMapper.updateById(user);
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
        wrapper.eq("open_pid",pid);
        User user = userMapper.selectOne(wrapper);
        return user;
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
            if (value.length > 1) {
                FileUtil.deleteFile(value[1]);
            }
        }
        String filePath = USER_PATH + "/" + id + "/" + AVATAR_PATH + "/" + file.getOriginalFilename();

        //添加新头像（oss）
        String fileUrl = FileUtil.saveFile(filePath, file);
        //添加新头像（mysql）
        QueryWrapper updateWrapper = new QueryWrapper();
        updateWrapper.eq("id",id);
        user.setAvatar(fileUrl);
        userMapper.update(user,updateWrapper);


        return fileUrl;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateById(user);
    }

    /**
     * 获取用户相册
     * @param id
     * @return
     */
    @Override
    public List<String> getAlbum(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
            return null;
        }
        String photoAlbum = user.getPhotoAlbum();
        List filUrlList = Arrays.asList(photoAlbum.split(","));
        return filUrlList;
    }

    /**
     * 相册插入图片
     * @param id 用户id
     * @param files 图片文件列表
     * @return 图片Url列表
     */
    @Override
    public List<String> insertAlbum(Integer id, List<MultipartFile> files) {
        User user = userMapper.selectById(id);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
            return null;
        }
        String fileBasePath = USER_PATH + "/" + id + "/" + ALBUM_PATH + "/";
        List<String> fileUrlList = files.stream()
                .map(item -> FileUtil.saveFile(fileBasePath + item.getOriginalFilename(), item))
                .collect(Collectors.toList());
        String oldAlbum = StringUtils.isEmpty(user.getPhotoAlbum()) ? "" : user.getPhotoAlbum() + "/";
        String newAlbum = oldAlbum + String.join(",", fileUrlList);
        user.setPhotoAlbum(newAlbum);
        userMapper.updateById(user);
        return fileUrlList;
    }

    /**
     * 删除相册中的指定图片
     * @param id 用户id
     * @param nums 删除图片的序号列表
     */
    @Override
    public List<String> deleteAlbum(Integer id, Integer nums) {
        User user = userMapper.selectById(id);
        if (user == null) {
            AssertUtil.failed("找不到该用户");
            return null;
        }
        List<String> album = Arrays.asList(user.getPhotoAlbum().split(","));
        StringBuffer newAlbum = new StringBuffer();
        for (int i = 0; i < album.size(); ++i) {
            if (nums == i) {
                String[] value = album.get(i).split(BASE_PATH+"/");
                if (value.length > 1) {
                    FileUtil.deleteFile(value[1]);
                }
            }
            else {
                if (newAlbum.length() == 0) {
                    newAlbum.append(album.get(i));
                }
                else {
                    newAlbum.append(",");
                    newAlbum.append(album.get(i));
                }
            }
        }
        user.setPhotoAlbum(newAlbum.toString());
        userMapper.updateById(user);
        return Arrays.asList(user.getPhotoAlbum().split(","));
    }

}
