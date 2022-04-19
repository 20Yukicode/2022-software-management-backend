package com.campus.love.message.util;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    private static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(20, 25, 100L,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());


    private static void run(ThreadPoolExecutor executor, Runnable... runnable) {

        CountDownLatch countDownLatch = new CountDownLatch(runnable.length);

        for (var item : runnable) {
            executor.submit(() -> {
                try {
                    item.run();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void run(Runnable... runnable) {

        CountDownLatch countDownLatch = new CountDownLatch(runnable.length);

        for (var item : runnable) {
            EXECUTOR.submit(() -> {
                try {
                    item.run();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void run(ThreadPoolExecutor executor, List<Runnable> runnable) {
        CountDownLatch countDownLatch = new CountDownLatch(runnable.size());

        for (var item : runnable) {
            executor.submit(() -> {
                try {
                    item.run();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}