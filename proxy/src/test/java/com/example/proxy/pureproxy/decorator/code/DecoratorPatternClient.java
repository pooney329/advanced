package com.example.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/**
 *  데코레이터패턴
 *  원래 서버가 제공하는 기능에 더해서 부가 기능을 수행한다.
 */
@Slf4j
public class DecoratorPatternClient {
    private Component component;

    public DecoratorPatternClient(Component component) {
        this.component = component;
    }

    public void execute(){
        String result = component.operation();
        log.info("result={}", result);
    }
}
