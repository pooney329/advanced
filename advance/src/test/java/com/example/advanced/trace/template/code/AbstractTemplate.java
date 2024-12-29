package com.example.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 메소드 패턴
 *  - 템플릿을 사용하는 방식으로 변하지 않는 부분을 모아두고, 일부 변하는 부분을 별도 호출해서 해결 하는 패턴
 */
@Slf4j
public abstract class AbstractTemplate {

    //변하지 않는 부분(시간측정)
    public void execute() {
        long startTime = System.currentTimeMillis();
        call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;
        log.info("resultTime={}", resultTime);
    }

    //변하는 부분
    protected abstract void call();
}
