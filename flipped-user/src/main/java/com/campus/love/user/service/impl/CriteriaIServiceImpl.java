package com.campus.love.user.service.impl;

import com.campus.love.user.entity.Criteria;
import com.campus.love.user.mapper.CriteriaMapper;
import com.campus.love.user.mapper.UserMapper;
import com.campus.love.user.service.CriteriaService;
import org.springframework.stereotype.Service;

@Service
public class CriteriaIServiceImpl implements CriteriaService {

    private final UserMapper userMapper;

    private final CriteriaMapper criteriaMapper;

    public CriteriaIServiceImpl(UserMapper userMapper, CriteriaMapper criteriaMapper) {
        this.userMapper = userMapper;
        this.criteriaMapper = criteriaMapper;
    }


    @Override
    public int insertOrUpdateCriteria(Criteria criteria) {
        //检查userId的合法性
        Integer id = criteria.getId();
        if (userMapper.selectById(id) == null) {
            return -1;
        }
        //检查是否已经存在Criteria
        return criteriaMapper.updateById(criteria) == 0 ? criteriaMapper.insert(criteria) : 0;
    }
}
