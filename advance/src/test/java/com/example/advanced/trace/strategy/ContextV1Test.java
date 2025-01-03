package com.example.advanced.trace.strategy;

import com.example.advanced.trace.strategy.code.strategy.ContextV1;
import com.example.advanced.trace.strategy.code.strategy.Strategy;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic1;
import com.example.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {
    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비지니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비지니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime = startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1(){
        Strategy strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        Strategy strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        contextV2.execute();
    }

    @Test
    void strategyV2(){
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1={}", strategyLogic1.getClass());
        contextV1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        log.info("strategyLogic2={}", strategyLogic2.getClass());
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        contextV2.execute();
    }

    @Test
    void strategyV3(){
        //단축기 ctrl + alt + n (인라인 value 적용)
        ContextV1 contextV1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });

        contextV1.execute();

        log.info("strategyLogic2={}", new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        }.getClass());
        ContextV1 contextV2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        contextV2.execute();
    }

    @Test
    void strategyV4(){
        //단축기 ctrl + alt + n (인라인 value 적용)
        ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        contextV1.execute();
        ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        contextV2.execute();
    }

}
