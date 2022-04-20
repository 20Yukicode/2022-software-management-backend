package com.campus.love.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class SynchronizedByKey<T> {

    private final Map<T, ReentrantLock> mutexCache=new ConcurrentHashMap<>();


    public void exec(T key,Runnable statement) {
        //log.info("mutexCache:{}",mutexCache.toString());
        ReentrantLock mutex4Key = null;
        ReentrantLock mutexInCache;
        do {
            if (mutex4Key != null) {
                mutex4Key.unlock();
            }
            mutex4Key = mutexCache.computeIfAbsent(key, k -> new ReentrantLock());
            mutex4Key.lock();
            mutexInCache = mutexCache.get(key);

        } while (mutexInCache == null || mutex4Key != mutexInCache);

        try {
            statement.run();
        } finally {
            if (mutex4Key.getQueueLength() == 0) {
                mutexCache.remove(key);
            }
            mutex4Key.unlock();
        }
    }
}
