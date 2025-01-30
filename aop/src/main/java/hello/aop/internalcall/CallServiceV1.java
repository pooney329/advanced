package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {


    private CallServiceV1 callServiceV1;


    /**
     * setter를 사용한 주입은 생성자로 하는 방식과 setter로하는 방식이 분리되어 있기 때문에
     * 빈을 다 생성 후 setter를 통해 자기자신을 주입힌다.
     *
     * [주의]
     *  - 스프링 2.6 부터는 기본적으로 순환참조를 금지시키고 있기 때문에 추가 설정 필요
     *      - applicatio.propertites 파일에 "spring.main.allow-circular-references=true" 추가
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    // 생성자 주입으로 생성 할 경우 순환참조 발생(자가 자신이 Bean으로 생성되지도 않았는데 빈을 넣을 수는 없다!!
//    @Autowired
//    public CallServiceV1(CallServiceV1 callServiceV1) {
//        this.callServiceV1 = callServiceV1;
//    }

    public void external() {
        log.info("call external");
        callServiceV1.internal();  // 외부 메소드 호출로 변경
    }

    public void internal() {
        log.info("call internal");
    }
}
