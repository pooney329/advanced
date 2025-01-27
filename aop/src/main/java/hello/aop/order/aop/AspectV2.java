package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Slf4j
@Aspect // 컴포넌트 스캔의 대상이 되지 않기 때문에  @Import, @Component, @Bean 등으로 직접 Bean으로 등록하는 작업이 필요하다.
public class AspectV2 {
    /**
     * pointcut 별도 분리 하는 방법
     *  - 다른 애스팩트에서 참고하려면 public으로 사용!
     *  - 하나의 포인트 컷을 여러 어드바이스에서 함께 사용 할 수 있다.
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature

    /**
     * @Around : 포인트 컷
     * doLog  : 어드바이스
     */
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
