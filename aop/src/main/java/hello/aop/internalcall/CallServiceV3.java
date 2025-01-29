package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 구조를 변경(분리) -> 내부 호출일경우 분리한다(가장 많이 사용).
 * [참고]
 * 
 * AOP는 주로 트랜잭션 적용이나 주요 컴포넌트의 로그 출력 기능에 사용된다. 쉽게 이야기해서 인터페이스에 메
 * 서드가 나올 정도의 규모에 AOP를 적용하는 것이 적당하다. 더 풀어서 이야기하면 AOP는 public 메서드에
 * 만 적용한다. private 메서드처럼 작은 단위에는 AOP를 적용하지 않는다.
 * AOP 적용을 위해 private 메서드를 외부 클래스로 변경하고 public 으로 변경하는 일은 거의 없다. 그러나
 * 위 예제와 같이 public 메서드에서 public 메서드를 내부 호출하는 경우에는 문제가 발생한다. 실무에서 꼭
 * 한번은 만나는 문제이기에 이번 강의에서 다루었다.
 * AOP가 잘 적용되지 않으면 내부 호출을 의심해보자.
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {
    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal();
    }


}
