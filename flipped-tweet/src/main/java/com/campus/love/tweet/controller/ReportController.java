package com.campus.love.tweet.controller;


import com.campus.love.common.core.api.MessageModel;
import com.campus.love.common.core.util.AssertUtil;
import com.campus.love.tweet.domain.vo.ReportVo;
import com.campus.love.tweet.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "举报模块")
@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @ApiOperation("举报某个东西")
    @PostMapping("")
    public MessageModel<Object> reportSomeThing(@RequestBody ReportVo reportVo) {
        AssertUtil.validateNull(reportVo.getReportedObjectId(), "举报对象Id不能为空");

        AssertUtil.validateNull(reportVo.getReportedType(), "举报类型不能为空");

        AssertUtil.validateNull(reportVo.getContent(), "举报内容不能为空");

        AssertUtil.validateNull(reportVo.getUserId(), "举报人的Id不能为空");

        reportService.insertReport(reportVo);
        return MessageModel.success();
    }
}
