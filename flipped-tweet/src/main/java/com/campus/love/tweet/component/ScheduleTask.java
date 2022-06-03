package com.campus.love.tweet.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@EnableScheduling
//@Configuration
public class ScheduleTask {

    @Scheduled(cron = "0 0 1 * * ?")
    private void test() {
        log.info("正在执行test");
    }


}
