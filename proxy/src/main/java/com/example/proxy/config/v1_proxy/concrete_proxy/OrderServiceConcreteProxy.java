package com.example.proxy.config.v1_proxy.concrete_proxy;

import com.example.proxy.app.v2.OrderRepositoryV2;
import com.example.proxy.app.v2.OrderServiceV2;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

/**
 * 클래스 기반 프록시의 단점
 * : super(null) 처럼 자바기본문법에 의해 자식 클래스를 생성 할 때는 항상 super()로 부모클래스의 생성자를 호출 해야한다.
 *
 */

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 target;
    private final LogTrace logTrace;



    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null); // 프록시는 보무객체의 기능을 사용하지 않기 때문에 즉, target을 호출해서 사용할 것이기 때문에 넣어줄 필요가 없음
        this.target = target;
        this.logTrace = logTrace;
    }


    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
