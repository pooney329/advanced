package com.example.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;


/**
 * 템플릿 메소드 패턴 중 변하는 부분을 처리하는 자식 클래스 (super.call())
 */
@Slf4j
public class SubClassLogic2 extends AbstractTemplate{

    @Override
    public void call() {
        log.info("비지니스 로직2 실행");
    }
}
