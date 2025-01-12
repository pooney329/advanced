package com.example.proxy.proxyfactory;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ConcreteService;
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
     *  1-3
     *      스프링 부트 버전에따라 다르지만 Aop를 적용할때 기본적으로 proxyTargetClass=true를 설정해서 사용한다.
     *      따라서 인터페이스가 있어도 항상 cglib를 사용해서 구체 클래스를 기반으로 프록시를 생성한다.
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



    @Test
    @DisplayName("구체클래스가 있으면 CGLIB 동적 프록시 사용")
    void concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        //프록시 팩토리로 만든 프록시 사용했을 때만 사용가눙
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    /**
     *  {@link ProxyFactory#setProxyTargetClass(boolean)}을 TRUE를 사용하면 인터페이스가 있어도 cglib를 사용한다"
     *  proxyClass=class com.example.proxy.common.service.ConcreteService$$SpringCGLIB$$0" 로 항상 나온다.
     */
    @Test
    @DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass(){

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        //프록시 팩토리로 만든 프록시 사용했을 때만 사용가눙
        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}

