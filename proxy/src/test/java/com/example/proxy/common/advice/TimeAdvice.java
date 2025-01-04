package com.example.proxy.common.advice;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  ProxyFactory 방식
 */
@Slf4j
public class TimeAdvice implements MethodInterceptor {
    /**
     * {@link MethodInvocation} 여기에 JDK 동적 , CGLIB에서 사용하던 target, 파라미터, 메소드 등이 다 들어 있다.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
//        Object result = method.invoke(target, args);
        Object result = invocation.proceed(); // 실제 객체를 찾아서 알아서 파라미터 넣고 실행함
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
