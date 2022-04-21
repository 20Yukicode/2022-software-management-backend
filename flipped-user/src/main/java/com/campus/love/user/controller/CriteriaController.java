package com.campus.love.user.controller;

import com.campus.love.common.core.api.MessageModel;
import com.campus.love.user.entity.Criteria;

import com.campus.love.user.service.CriteriaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class CriteriaController {

    private final CriteriaService criteriaService;

    public CriteriaController(CriteriaService criteriaService) {
        this.criteriaService = criteriaService;
    }

    @PostMapping("/criteria")
    public MessageModel<Integer> alterCriteria(@RequestBody Criteria criteria) {
        int result = criteriaService.insertOrUpdateCriteria(criteria);
        switch (result){
            case -1: {
                return MessageModel.failed("用户不存在");
            }
            case 0: {
                return MessageModel.success("更新成功",result);
            }
            default:{
                return MessageModel.success("插入成功",result);
            }
        }
    }
}
