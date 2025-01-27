package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 *  동일한 @Aspect 안에서는 조인포인트의 우선순위 로 동작한다.
 *  [동작 우선 순서]
 *      1. Around -> Before -> After -> AfterReturning -> AfterThrowing
 *          - 동일한 종류의 어드바이스가 2개 있으면 순서가 보장되지 않기 떄문에 이경우 @Aspect 클래스를 분리하고 @Order 적용
 *
 *  [Around 이외에 다른 어드바이스가 있는 이유]
 *  - proceed()를 호출하지 않는 실수를 방지 하기 위함 -> 실수로 호출 하지 않은 경우 장애가 발생.
 *  - 의도를 명확히 할 수 있다  -> @Before의 경우 타겟 전에 동작해야 하는 의도가 명확함!
 *    : 좋은 설계란 제약이 있는 것 -> 제약 덕분에 역할이 명확해지고, 코드의 의도를 파악하기 쉬움
 */
@Slf4j
@Aspect
public class AspectV6Advice {
    /**
     * @Around : 나머지 어드바이스와 다르게 ProceedingJoinPoint.proceed() 를 호출 해야 다음 대상이 호출 된다.
     *  - 조인 포인트의 실행 여부를 선택 가능 (proceed 호출 하지 않으면 된다)
     *  - 전달값 변환 가능
     *  - 예외 변환 가능
     *  - 첫번쨰 파리미터는 반드시 ProceedingJoinPoint를 사용해야 한다.
     *  - proceed()를 여러번 재호출 가능!
     */
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[트랜잭션 시작] {},", joinPoint.getSignature());
            Object result = joinPoint.proceed();;
            //@AfterReturning
            log.info("[트랜잭션 종료] {},", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[리소스 릴리즈] {},", joinPoint.getSignature());
        }
    }

    /**
     * ProceedingJoinPoint.proceed()를 사용하지 않는다. 메소드 종료시 자동으로 다음 타겟이 호출 횐다.
     */
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void deBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     *  @AfterReturning :정상 리턴을 받았을때 동작 , 리턴값을 조작하지 못한다(반환 객체를 조작은 가능 set사용 하거나), 단 조작하고자 할때는 @Around 사용해야한다.
     *  - 파라미터 'Object result' 의 경우 타입이 같거나 상위 타입이어야 한다. void 대상 호출시에는 Object로 받아서 사용하면 정상 호출 된다.
     *     만약 대상 메소드 리턴 타입이 String 이고 'Integer object' 파라미터로 받는 다면 해당 어드바이스는 호출 되지 않는다.
     *
     *
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    /**
     * @AfterThrowing :  예외를 던저서 종료될때 사용
     *  - 호출메소드의 반환 타입이랑 파라미터 'Exception ex' 와 동일 해야 동작! (부모타입을 지정하면 모든 자식 타입은 인정된다)
     */
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[ex] {} message={}", ex);
    }

    /**
     *  @After : 메소드 실행이 종려되면 실행(finally 와 동일)
     */
    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after] {}", joinPoint.getSignature());
    }

}
