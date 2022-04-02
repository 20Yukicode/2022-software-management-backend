package com.campus.love.tweet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.love.tweet.entity.Comment;
import org.springframework.stereotype.Repository;

/**
* @author nzh
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2022-04-02 17:37:02
* @Entity com.campus.love.tweet.entity.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}




