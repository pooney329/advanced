package com.example.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasePostProcessorTest {

    @Test
    void basicConfig(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        //A는 빈으로 등록된다
//        A a = applicationContext.getBean("beanA", A.class);
//        a.helloA();
        //B는 빈으로 등록되지 않는다.
//        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class));


        //beanA라는 이름으로 B 객체가 빈으로 등록된다.
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));

    }


    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {
        @Bean(name ="beanA")
        public A a(){
            return new A();
        }
        @Bean
        public AtoBPostProcessor helloPostProcessor(){
            return new AtoBPostProcessor();
        }


    }
    @Slf4j
    static class A {
        public void helloA(){
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB(){
            log.info("hello B");
        }
    }

    /**
     * 빈 후처리기로 스프링이 빈으로 등록하면 컨테이너가 빈 후처리기로 인식하고 등록한다.
     *   - beanA라는 이름에 B라는 객체를 빈으로 등록하는 등의 처리가 가능하다.
     *   - 빈 후처리기는 빈을 조작하고 변경 할 수 있는 후킹 포인트이다.
     *   - 빈 객체를 조작하거나 심지어 다른 객체로 바꾸어 버릴 수 있을 정도록 막강하다.
     *   - 특히 컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없었는데, 빈 후처리기를사용하면 개발자가 등록한 보든 빈을 중간에 조작할 수 있다.(빈 객체를 프록시로 교체 가능)
     */
    @Slf4j
    static class AtoBPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {


        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }
}
