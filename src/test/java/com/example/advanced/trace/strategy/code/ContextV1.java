package com.example.advanced.trace.strategy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * == 전략 패턴 ==
 * 필드에 전략을 보관하는 방식
 *  -> 변하지 않는 로직을 가지고 있는 템플릿 역할을 하는 코드 문맥은 크게 변하지 않지만 일부 전략이 변경된다.
 *  -> spring 의존 주입과 비슷하다.
 *
 *  즉, 변하지 않는 부분을 Context에 두고 변하는 부분을 Strategy를 구현해서 만든다.
 *
 *  문제는
 *   1. 선조립후 실행이기 때문에 전략을 변경하기 어렵다
 *   2. 싱글톤으로 사용하는 경우 동시성 이슈 발생 -> 사용하고자 할때 new로 생성 해서 사용
 *
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
