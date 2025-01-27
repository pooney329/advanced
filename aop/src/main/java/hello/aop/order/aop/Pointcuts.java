package hello.aop.order.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    /**
     * pointcut 별도 분리 하는 방법
     *  - 다른 애스팩트에서 참고하려면 public으로 사용!
     *  - 하나의 포인트 컷을 여러 어드바이스에서 함께 사용 할 수 있다.
     */
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder(){} //pointcut signature

    /**
     * 클래스 이름 패턴이 *Service
     */
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
