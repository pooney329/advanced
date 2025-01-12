package com.example.proxy.advisor;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorTest {


    /**
     *  1-1
     *   Advisor는 하나의 포인트 컷과 하나의 어드바이스로 구성된다.
     *
     * {@link DefaultPointcutAdvisor}는 'Advisor' 인터페이스의 가장 일반적인 구현체이다.
     * {@link ProxyFactory#addAdvisor(Advisor)}의 내부 로직을 보면 결국 {@link DefaultPointcutAdvisor} 라는 Advisor를 사용한다.
     * 결국 addAdvisor는 사용하기 쉽게 제공하는 편의 메소드이다.
     *
     */
    @Test
    void advisorTest1(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
