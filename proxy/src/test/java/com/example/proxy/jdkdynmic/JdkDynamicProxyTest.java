package com.example.proxy.jdkdynmic;

import com.example.proxy.jdkdynmic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {


    /**
     *  AImpl, BImpl 각각 프록시를 생성하지 않고 jdk 동적 프록시를 사용해서 동적으로 만들고
     *  {@link TimeInvocationHandler} 는 공통으로 사용했다. jdk 동적 프록시 덕분에 적용 대상 만큼
     *  프록시 객체를 만들지 않아도 된다
     */
    @Test
    void dynamicA(){
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        //자바에서 제공하는 프록시 생성 기술
        //Object proxy = Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB(){
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        //자바에서 제공하는 프록시 생성 기술
        //Object proxy = Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);
        proxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
