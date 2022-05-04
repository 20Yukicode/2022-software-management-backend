package com.campus.love.common.mq.component.tweet;

import com.campus.love.common.mq.domain.dto.CommentMqDto;

public interface INumConsumer {

    interface IComment {

        void consumeCommentNum(CommentMqDto commentMqDto);

        void consumeLikesNum(CommentMqDto commentMqDto);
    }

    interface ITweet {

        void consumeCommentNum(CommentMqDto commentMqDto);

        void consumeLikesNum(CommentMqDto commentMqDto);
    }
}
