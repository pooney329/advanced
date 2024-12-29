package com.example.advanced.trace.strategy.code.template;

import com.example.advanced.trace.strategy.code.strategy.Strategy;
import lombok.extern.slf4j.Slf4j;


/**
 *  템플릿 콜백 패턴
 *  ex) RestTemplate , xxTemplate 등..
 */
@Slf4j
public class TimeLogTemplate {
    public void execute(Callback callback) {
        long startTime = System.currentTimeMillis();
        callback.call(); //위임
        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;
        log.info("resultTime={}", resultTime);
    }
}
