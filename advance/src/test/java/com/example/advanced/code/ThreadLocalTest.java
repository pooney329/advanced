package com.example.advanced.code;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ThreadLocalTest {
    private final ThreadTest threadTest = new ThreadTest();
    @Test
    void concurrency() {
        Runnable runnable1 = () -> threadTest.callPooney("pooneyA");
        Runnable runnable2 = () -> threadTest.callPooney("pooneyB");

        Thread thread1 = new Thread(runnable1);
        thread1.setName("pooney-A");
        Thread thread2 = new Thread(runnable2);
        thread2.setName("pooney-B");
        thread1.start();
        thread2.start();
        CommonUtils.sleep(5000);
    }
}
