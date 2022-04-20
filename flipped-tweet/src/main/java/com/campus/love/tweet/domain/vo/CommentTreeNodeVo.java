package com.campus.love.tweet.domain.vo;


import com.campus.love.tweet.domain.bo.CommentBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 不是树形结构
 *
 * T要么是CommentTreeNodeVo(此时是多叉树结构）
 * 要么是CommentVo（此时是一个列表）
 */
@Data
public class CommentTreeNodeVo<T> {

    @ApiModelProperty("评论实体并且包含用户信息")
    private CommentBo commentBo;

    @ApiModelProperty("评论子节点")
    private List<T> childCommentNodes;

    public CommentTreeNodeVo(CommentBo comment, List<T> childCommentNodes) {
        this.commentBo = comment;
        this.childCommentNodes = childCommentNodes;
    }
}
