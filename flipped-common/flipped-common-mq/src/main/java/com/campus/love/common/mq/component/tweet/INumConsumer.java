package com.campus.love.common.mq.component.tweet;

import com.campus.love.common.mq.domain.dto.CommentDto;

public interface INumConsumer {

    interface IComment {

        void consumeCommentNum(CommentDto commentDto);

        void consumeLikesNum(CommentDto commentDto);
    }

    interface ITweet {

        void consumeCommentNum(CommentDto commentDto);

        void consumeLikesNum(CommentDto commentDto);
    }
}
