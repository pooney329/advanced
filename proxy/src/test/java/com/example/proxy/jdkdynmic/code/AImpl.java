package com.example.proxy.jdkdynmic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AImpl implements AInterface{

    @Override
    public String call() {
        log.info("A호출");
        return "A";
    }
}
