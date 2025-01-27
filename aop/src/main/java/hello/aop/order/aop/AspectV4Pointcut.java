

package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트 컷을 외부에서 사용 할 수 있는 방법
 */
@Slf4j
@Aspect
public class AspectV4Pointcut {


    /**
     * @Around : 포인트 컷
     * doLog  : 어드바이스
     */
    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public  Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try{
            log.info("[트랜잭션 시작] {},", joinPoint.getSignature());
            Object result = joinPoint.proceed();;
            log.info("[트랜잭션 종료] {},", joinPoint.getSignature());
            return result;
        } catch (Exception e){
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw  e;
        } finally {
            log.info("[리소스 릴리즈] {},", joinPoint.getSignature());
        }
    }
}
