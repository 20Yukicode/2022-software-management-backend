package com.campus.love.message.service.impl;

import com.campus.love.message.entity.ChatRecord;
import com.campus.love.message.service.ChatService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final MongoTemplate mongoTemplate;

    private final static String databaseName="campus";

    public ChatServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private List<ChatRecord> chatRecords(Integer firstUserId, Integer secondUserId){
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("firstUserId").is(firstUserId),
                Criteria.where("secondUserId").is(secondUserId));
        return mongoTemplate
                .find(new Query(criteria), ChatRecord.class, databaseName);
    }

    public List<ChatRecord> getChatRecord(Integer firstUserId, Integer secondUserId) {

        return chatRecords(firstUserId,secondUserId);
    }

    @Override
    public void insertChatRecord(Integer firstUserId, Integer secondUserId,ChatRecord oneChatRecord) {
        List<ChatRecord> chatRecords = chatRecords(firstUserId, secondUserId);
        oneChatRecord.setCreateTime(new Date());
        chatRecords.add(oneChatRecord);
        mongoTemplate.save(chatRecords);
    }


}
