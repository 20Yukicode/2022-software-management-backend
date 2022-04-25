package com.campus.love.tweet.domain.vo;

import com.campus.love.tweet.enums.Operator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NotNull
public class AddVo{

    @NotNull
    @ApiModelProperty("评论时间")
    private Date createTime;

    @NotNull
    @ApiModelProperty("用户Id")
    private Integer userId;

    /**
     * 这里是给动态还是评论 回复
     */
    @NotNull
    @ApiModelProperty("类型(动态或评论)")
    private Operator operator;

}
