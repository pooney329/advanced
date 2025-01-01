package com.example.proxy.config.v1_proxy.concrete_proxy;

import com.example.proxy.app.v2.OrderControllerV2;
import com.example.proxy.app.v2.OrderServiceV2;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

public class OrderContorllerConcreteProxy extends OrderControllerV2 {

    private final OrderControllerV2 target;
    private final LogTrace logTrace;

    public OrderContorllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        String result;
        try {
            status = logTrace.begin("OrderController.request()");
            //target 호출
            result = target.request(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
        return result;
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
