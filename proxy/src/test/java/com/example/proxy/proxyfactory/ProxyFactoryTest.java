package com.example.proxy.proxyfactory;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    /**
     *  1-1
     *      new ProxyFactory(target)을 통해 생성자에 프록시의 호출 대상을 넘겨준다.
     *      만약 인터페이스가 있으면 JDK 동적 프록시를 사용하고 구체 클래스가 있으면 CGLIB를 통해서 동적 프록시를 생성한다.
     *      target이 {@link ServiceInterface}를 가지고 있기 때문에 JDK 방식 사용
     *
     *  1-2
     *      proxyFactory.addAdvice(new TimeAdvice()) 를 통해 프록시 팩토리를 통해서
     *      만든 프록시가 사용할 부가 기능 로직을 정의한다, JDK(InvocationHandler), CGLIB(MethodInterceptor) 방식과 유사하다.
     *
     *
     */
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        //프록시 팩토리로 만든 프록시 사용했을 때만 사용가눙
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }
}

