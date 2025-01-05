package com.example.proxy.jdkdynmic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    /**
     * 공통로직1과 로직2는 호출하는 메서드만 다르고 전체코드의 흐름은 똑같다
     * {hello.callA(), hello.callA()} 이부분을 동적으로 처리 할 수 있다면 문제 해결 가능하다.
     * 이럴때 사용 할 수 있는 방법이 리플렉션이다. 물론, 람다를 이용해서 공통화하는 것도 가능하다.
     */
    @Test
    void reflection0() {
        Hello hello = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = hello.callA(); //호출하는 메소드가 다름
        log.info("reulst={}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = hello.callB(); //호출하는 메소드가 다름
        log.info("reulst={}", result2);
        //공통 로직2 종료
    }

    /**
     *  Class.forName : 클래스 메타정보를 획득한다. 내부 클래스는 구분을 위해 '$'를 사용한다
     *  classHello.getMethod : 해당 클래스의 'callA' 메서드 메타정보를 획득한다
     *  methodCallA.invoke(target) : 획득한 메소드 메타정보를 실제 인스턴스의 메서드를 호출한다.
     *
     *  -> 굳이 메소드를 직접 호출하면 되는데 메서드 정보를 획득해서 메서드를 호출하면 어떤 효과가 있을까?
     *  : 클래스나 메서드 정보를 동적으로 변경할 수 있다는 점
     */

    @Test
    void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //클래스 정보
        Class<?> classHello = Class.forName("com.example.proxy.jdkdynmic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서도 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        //callB 메서도 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }


    @Test
    void reflection2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //클래스 정보
        Class<?> classHello = Class.forName("com.example.proxy.jdkdynmic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서도 정보 (Method 부분을 추상화 해서 공통화 가능)
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //callB 메서도 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    /**
     * 공통 로직1,로직2를 한번에 처리 할 수 있는 통합된 공통 처리 로직이다.
     * 기존에는 메서드 이름을 직접 호출 했지만(callA,callB) 이제는 Method 라는 메타정보를 통해서 호출할 메서드 정보가 동적으로 제공된다.
     * 정리하면, target.callA() , target.callB() 코드를 리플렉션을 사용해서 Method 라는 메타정보를 추상화했고 공통로직을 만들었다.
     *
     *  ##주의##
     *  런타임에 동작하기 때문에 컴파일 시점에 오류를 잡을 수 없다.
     *
     */
    private void dynamicCall(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(object);
        log.info("result1={}", result);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
