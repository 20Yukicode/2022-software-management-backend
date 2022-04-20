package com.campus.love.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.love.user.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {

    List<String> getSubscribedById(@Param("userId")Integer userId);

}




