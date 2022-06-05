package com.campus.love.user.service;

import com.campus.love.user.entity.Criteria;
import org.springframework.stereotype.Service;

@Service
public interface CriteriaService {

    int insertOrUpdateCriteria(Criteria criteria);

    Criteria getCriteria(Integer id);
}
