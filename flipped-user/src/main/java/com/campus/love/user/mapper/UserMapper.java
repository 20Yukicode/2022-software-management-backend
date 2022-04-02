package com.campus.love.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.love.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author 86180
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-04-02 14:49:32
* @Entity com.campus.love.user.entity.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<String> getSubscribedById(@Param("userId")Integer userId);
}




