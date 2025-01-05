package com.example.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 클래스 기반 프록시 도입
 */
@Slf4j
public class ConcreteLogic {
    public String operation(){
        log.info("ConcreteLogic 실행");
        return "data";
    }
}
