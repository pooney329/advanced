package com.example.proxy.config.v5_autoproxy;

import com.example.proxy.config.AppV1Config;
import com.example.proxy.config.AppV2Config;
import com.example.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import com.example.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 프록시 자동 생성기는 프록시를 하나만 생성하고 Advisor를 여러개를 갖는다.
 * [case 정리]
 *      case 1)  qdvisor1의 포인트컷만 만족하는 경우
 *           ->  프록시 1개를 생성하고 해당 프록시에 advisor1만 넣는다.
 *      case 2)  qdvisor1,2의 포인트컷 모두 만족하는 경우
 *           ->  프록시 1개를 생성하고 해당 프록시에 advisor1,2 모두 넣는다.
 *      case 3)  qdvisor1,2의 포인트컷 모두 만족 하지 않는 경우
 *          ->  프록시 생성 하지 않는다.
 */
@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {
    /**
     * aop를 디펜던시 받음으로써 AutoProxyCreator라는 빈후처리기를 자동으로 등록을 해주고
     * 빈으로 등록된 Advisor를 찾고 포인트 컷을 비교하여 메소드가 하나라도 해당이되면 프록시를 생성하도록 된다.
     *
     *  [중요]
     *  포인트 컷이 사용되는 범위
     *      - 프록시를 적용요부판단
     *          : (클래스 + 메소드) 조건을 모두 비겨하여 조건이 맞는게 하나라도 있다면 프록시 생성 아니면 프록시를 생성 하지 않는다.
     *      - 어드바이스 적용 여부 판단
     *          : 프록시가 호출 되었을때 부가 기능인 어드바이스를 적욜하지 판단
     *              1. request()를 호출 할 경우 포인트컷 조건에 만족함으로 프록시는 어드바이스를 먼저 호출 하고 target을 호출
     *              2. noLog()를 호출 할 경우 포인트 컷 조건에 만족하지 않음으로 어드바이스를 호출 하지 않고 target만 호출
     *
     *
     */
//    @Bean
    public Advisor getAdvisor1(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }


    /**
     *  pointcut.setMappedNames("request*", "order*", "save*"); 이런식으로 처리 했을 경우
     *  spring에서 자동 등록하는 bean 중에 메소드가 request로 시작하는 것들도 모두 찍어버리는 문제가 발생한다.
     *  이것을 해결 하기 위해 정밀하게 pointCut을 설정 할 수 있도록 {@link AspectJExpressionPointcut} 를 사용한다.
     * @param logTrace
     * @return
     */
//    @Bean
    public Advisor getAdvisor2(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.proxy.app..*(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     *  getAdvisor2()는 no-log도 출력하기 때문에 no-log는 제외하기 위한 설정 추가
     * @param logTrace
     * @return
     */
    public Advisor getAdvisor3(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.example.proxy.app..*(..)) && !execution(* com.example.proxy.app..noLog(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }



}
