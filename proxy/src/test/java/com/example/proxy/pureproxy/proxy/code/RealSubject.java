package com.example.proxy.pureproxy.proxy.code;

import com.example.proxy.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject{

    @Override
    public String operation() {
        log.info("실제 객체 호출");
        CommonUtils.sleep(1000);
        return "data";
    }
}
