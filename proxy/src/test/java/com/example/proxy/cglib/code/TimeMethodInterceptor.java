package com.example.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  MethodInterceptor 인터페이스를 구현해서 CGLIB 프록시의 실행 로직을 정의한다
 */
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {
    private Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     *  {@link MethodProxy proxy} 를 사용하면 성능이 더좋다고 한다. 그래서  {@link Method method} 를 사용을 잘 안한다.
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
        Object result = proxy.invoke(target, args);
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
