package com.example.proxy.config.v1_proxy.concrete_proxy;

import com.example.proxy.app.v2.OrderControllerV2;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;


/**
 * 인터페이스 기반 프록시와 클래스 기반 프록시
 *
 * [차이점]
 * 클래스 기반 프록시는 해당 클래스에만 적용 가능하지만, 인터페이스 기반 프록시는 인터페이스만 같으면 모든 곳에 적용가능
 * 클래스 기반 프록시는 상속을 사용하기 때문에 몇가지 제약이 존재
 *  - 부모 클래스의 생성자를 호출 해야한다
 *  - 클래스에 final 키워드가 붙으면 상속이 불가능
 *  - 메서드에 final 키워드가 붙으면 해당 메서드를 오버라이딩 할 수 없다.
 *
 *
 * 결론) 인터페이스 기반이 구현과 역할을 명확하게 나우어 더 좋다. 단점은 인터페이스가 필요하다는 것
 *      하지만 구현을 변경할 가능성이 거의 없는 코드에 무작정 인터페이스를 사용하는 것은 실용적이지 않다.
 *
 *
 */
public class OrderControllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target;
    private final LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController.request()");
            //target 호출
            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }

    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
