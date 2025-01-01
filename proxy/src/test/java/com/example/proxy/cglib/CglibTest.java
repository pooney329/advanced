package com.example.proxy.cglib;

import com.example.proxy.cglib.code.TimeMethodInterceptor;
import com.example.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 *  JDK 동적 프록시 CGLIB 차이점
 *  : JDK 동적 프록시는 인터페이스를 구현해서 프록시를 생성하지만, Cglib는 구체 클래스를 상속 해서 프록시를 생성한다.
 */
@Slf4j
public class CglibTest {
    /**
     * {@link Enhancer} 를 사용해서 프록시를 생성한다.
     *  setSuperclass : CGLIB는 구체 클래스를 상속 받아서 프록시를 생성 할 수 있다. 어떤 구체 클래스를 상속 받을지 결정
     *  setCallback   : 프로시에 적용할 실행 로직을 할당한다.
     *  create        : 프록시 생성 supperClass에서 지정한 클래스를 상속 받아서 프록시가 만들어진다.
     *
     *  [문제점]
     *  : 클래스 기반으로 상속을 사용을 사용하기 때문에 제약이 있다.
     *  1. 부모 클래스의 생성자를 체크해야한다. 동적 생성을 하기 때문에 '기본생성자'가 필요 (기본생성자를 생성하고 setter를 통해서 주입해야 할 수도..)
     *  2. final 키워드가 붙으면 상속, 오버로딩 불가능
     *   -> 때문에 ProxyFactory를 통해서 CGLIB를 사용하면 이러한 단점을 어느정도 해결 하다.
     */
    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}

