package com.campus.love.user.service;


import com.campus.love.user.entity.Subscribed;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscribedService {

    Subscribed insertSubscribed(Subscribed subscribed);

    Integer deleteSubscribed(int firstUserid, int secondUserid);

    Subscribed getSubscribed(int firstUserid, int secondUserid);

    List<Subscribed> getSubscribedList(int userId);
}
