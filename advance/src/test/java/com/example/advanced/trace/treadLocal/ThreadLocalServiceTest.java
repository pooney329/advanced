package com.example.advanced.trace.treadLocal;

import com.example.advanced.trace.treadLocal.code.FieldService;
import com.example.advanced.trace.treadLocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService fieldService = new ThreadLocalService();

    @DisplayName("동시성 문제 발생 없는 경우")
    @Test
    void field(){
        log.info("main start");;
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA= new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB= new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(2000);
        threadB.start();

        sleep(2000); //메인 쓰레드 종료 대기
        log.info("main exit");
    }

    @DisplayName("동시성 문제 발생 한 경우")
    @Test
    void field_concurrency(){
        log.info("main start");;
        Runnable userA = () -> {
            fieldService.logic("userA");
        };

        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA= new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB= new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);
        threadB.start();

        sleep(2000); //메인 쓰레드 종료 대기
        log.info("main exit");
    }




    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
