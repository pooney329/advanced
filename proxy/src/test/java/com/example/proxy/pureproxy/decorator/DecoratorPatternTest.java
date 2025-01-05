package com.example.proxy.pureproxy.decorator;

import com.example.proxy.pureproxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 *  [프록시 패턴과 데코레이터 패턴의 차이]
 *  차어는 패턴을 만든 의도 이다 두개는 모양이 거의 같다.
 *  프록시 패턴 : 다른 객체에 대한 '접근제어' 하기 위해 대리자를 제공
 *  데코레이터 패턴: 객체에 추가 책임(기능)을 동적으로 추가 하고 기능 확장을 위한 유연한 대안 제공
 *  정리) 프록시가 접근제어 목적이라면 프록시 패턴 , 새로운 기능을 추가하는 것이 목적이라면 데코레이터패턴
 */
@Slf4j
public class DecoratorPatternTest {
    @Test
    void noDecorator(){
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    /**
     * 응답값을 꾸며주는 데이터코레이터 패턴
     */
    @Test
    void decorator1(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    /**
     * client -> timeDecorator -> messageDecorator -> realComponent
     */
    @Test
    void decorator2(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }

    @Test
    void decorator3(){
        Component realComponent = new RealComponent();
        Decorator messageDecorator = new MessageDecorator(realComponent);
        Decorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }

}


