package com.example.proxy.config.v6_app.aspect;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;


/**
 * spring이 @Aspect을 보고 이후  @Around와 스코프안에 내용을 보고 Advisor를 만든다!
 */
@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    /**
     * {@link @Around}는 포인트 컷 , 스코프안에 내용은 Advice 라고 생각 하면 된다.
     * 이걸 가지고 결국 Advisor를 만든다.
     */
    @Around("execution(* com.example.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
        log.info("getArgs={}", joinPoint.getArgs()); // 전달 인자
        log.info("getSignature={}", joinPoint.getSignature()); // join point 시그니처

        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed(); //실제 호출 대상을 호출

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
