package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    /**
     * ApplicationContext는 너무 거대하여 사용을 자제하는 것이 좋다.
     */
//    private final ApplicationContext applicationContext;
//    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }


    /**
     *  ApplicationContext보다는 ObjectProvider를 사용하는 것이 좀더 낫다
     *  ObjectProvider는 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라 실제 객체를 사용하는 시점으로 지연 할 수 있다.
     *  때문에 callServiceProvider.getObject()를 호출 하는 시점에 스프링 컨테이너에서 빈을 조회한다. -> 자기 자신을 주입 받는 것아 아니기 때문에 순환 사이클이 생기지 않는다.
     */
    private final ObjectProvider<CallServiceV2> callServiceProvider;
    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }



    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class); //지연 조회 방식 사용
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();  // 외부 메소드 호출로 변경
    }

    public void internal() {
        log.info("call internal");
    }
}
