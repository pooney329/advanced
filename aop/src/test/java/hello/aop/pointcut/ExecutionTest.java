package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut(); //포인트 컷 표현식을 처리해주는 클래스
    Method helloMethod;


    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        /**
         * helloMethod {} = public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
         * -> 저걸 기준으로 execution(String ..package..Class......) 를 매칭하고 포인트컷 대상을 찾아 낸다!!!
         */
        log.info("helloMethod={}", helloMethod);
    }
}
