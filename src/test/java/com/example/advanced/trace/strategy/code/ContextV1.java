package com.example.advanced.trace.strategy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식
 *  -> 변하지 않는 로직을 가지고 있는 템플릿 역할을 하는 코드 문맥은 크게 변하지 않지만 일부 전략이 변경된다.
 *  -> spring 의존 주입과 비슷하다.
 */
@Slf4j
public class ContextV1 {
    private final Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    //변하지 않는 부분
    public void execute() {
        long startTime = System.currentTimeMillis();
        strategy.call(); //위임
        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;
        log.info("resultTime={}", resultTime);
    }
}
