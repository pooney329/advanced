package com.example.advanced.code;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import static com.example.advanced.code.CommonUtils.*;

@Slf4j
@Configuration
public class ThreadTest {
    public ThreadLocal<String> pooneyValue = new ThreadLocal<>(); // (1)

    private void updatePooneyValue(String value) {
        pooneyValue.set(value); // (2)
    }

    private String getPooneyValue() {
        return pooneyValue.get(); // (3)
    }

    public String callPooney(String value) {
        log.info("저장할 value : {}", value);
        log.info("현재 쓰레드 명 : {}", Thread.currentThread().getName());
        updatePooneyValue(value);
        sleep(2000);
        String pooneyValue = getPooneyValue();
        log.info("조회한 value value : {}", pooneyValue);
        return pooneyValue;
    }

    public void clear(){
        pooneyValue.remove();
    }
}
