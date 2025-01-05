package com.example.proxy.config.v1_proxy.interface_proxy;

import com.example.proxy.app.v1.OrderControllerV1;
import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {
    private final OrderControllerV1 target;
    private final LogTrace logTrace;

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
