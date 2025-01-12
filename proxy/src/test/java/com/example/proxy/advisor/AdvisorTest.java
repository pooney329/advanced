package com.example.proxy.advisor;

import com.example.proxy.common.advice.TimeAdvice;
import com.example.proxy.common.service.ServiceImpl;
import com.example.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {


    /**
     * 1-1
     * Advisor는 하나의 포인트 컷과 하나의 어드바이스로 구성된다.
     * <p>
     * {@link DefaultPointcutAdvisor}는 'Advisor' 인터페이스의 가장 일반적인 구현체이다.
     * {@link ProxyFactory#addAdvisor(Advisor)}의 내부 로직을 보면 결국 {@link DefaultPointcutAdvisor} 라는 Advisor를 사용한다.
     * 결국 addAdvisor는 사용하기 쉽게 제공하는 편의 메소드이다.
     */
    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    /**
     * new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice()); 과 같이 {@link Pointcut.TRUE} 를 사용하면 항상 대상이 된다.
     */

    @Test
    @DisplayName("직접 만든 포인트 컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    /**
     *  스프링이 제공하는 포인트 컷 중 하나인 이름으로 찾는 NameMatchMethodPointcut를 사용한다
     *  - JdkRegexpMethodPointCut
     *  - TruePointcut
     *  - AnnotationMatchingPointcut
     *  - AspectJexpressionPointcut
     */
    @Test
    @DisplayName("스프링이 제공하는 포인트 컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    /**
     * 직접 구현한 포인트 컷
     * 항상 클래스 필터는 True를 반환
     */
    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {

            boolean result = method.getName().equals(matchName);
            log.info("포인트 컷 호출 method={} targetClass ={}", method.getName(), targetClass);
            log.info("포인트 컷 결과 result={}", result);
            return result;
        }

        /**
         * isRuntime()의 값에 따라 메소드 호출이 달라진다.
         * false : {@link MethodMatcher#matches(Method, Class)} 를 호출하게 되고 클래스의 정적 정보만 사용 하기 때문에 스프링이 내부에서 캐싱을 통한 성능 향상이 가능
         * true  : {@link MethodMatcher#matches(Method, Class, Object...)} 를 호출 하게 되고 매개변수가 동적으로 변경된다고 가정하기 때문에 캐싱을 하지 않는다.
         *
         * @return
         */
        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
