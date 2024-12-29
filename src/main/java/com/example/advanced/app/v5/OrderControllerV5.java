package com.example.advanced.app.v5;

import com.example.advanced.trace.callback.TraceCallback;
import com.example.advanced.trace.callback.TraceTemplate;
import com.example.advanced.trace.logtrace.LogTrace;
import com.example.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderControllerV5 {
    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;


    /**
     * 생성자로 의존 관계 주입을 받거나 TraceTemplate를 스프링 빈으로 등록하고 주입 받아도 된다.
     * -> 이방법의 장점은 테스트 코드 작성이 편함
     */
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return traceTemplate.execute("OrderController.request()", () -> {orderService.orderItem(itemId); return "ok";});
    }
}
