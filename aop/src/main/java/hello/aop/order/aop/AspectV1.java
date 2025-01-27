package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Slf4j
@Aspect // 컴포넌트 스캔의 대상이 되지 않기 때문에  @Import, @Component, @Bean 등으로 직접 Bean으로 등록하는 작업이 필요하다.
public class AspectV1 {
    /**
     * @Around : 포인트 컷
     * doLog  : 어드바이스
     */
    @Around("execution(* hello.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
