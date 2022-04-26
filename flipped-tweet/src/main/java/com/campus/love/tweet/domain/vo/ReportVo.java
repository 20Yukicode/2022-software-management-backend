package com.campus.love.tweet.domain.vo;

import com.campus.love.tweet.enums.ReportedType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReportVo {

    @ApiModelProperty("举报对象的Id")
    private Integer reportedObjectId;

    @ApiModelProperty("举报类别")
    private ReportedType reportedType;

    @ApiModelProperty("举报内容类型")
    private String violateType;

    @ApiModelProperty("举报内容")
    private String content;

    @ApiModelProperty("举报人的Id")
    private Integer userId;

}
