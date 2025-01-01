package com.example.proxy.jdkdynmic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BImpl implements BInterface{

    @Override
    public String call() {
        log.info("B호출");
        return "B";
    }
}
