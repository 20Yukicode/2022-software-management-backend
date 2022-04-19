package com.campus.love.tweet.domain.vo;

import com.campus.love.tweet.entity.Tweet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TweetAndCommentVo {

    @ApiModelProperty("动态实体")
    private Tweet tweet;

    @ApiModelProperty("评论子节点")
    private List<CommentTreeNodeVo> comments;
}
