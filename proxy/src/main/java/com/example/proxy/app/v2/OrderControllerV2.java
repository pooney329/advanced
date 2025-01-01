package com.example.proxy.app.v2;

import com.example.proxy.app.v1.OrderServiceV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
//@RequestMapping // 컴포넌트 스캔 대상에 포함되지 않아서 @Controller 대신 사용
//@ResponseBody
@RestController
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;


    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    public String request(@RequestParam("itemId") String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
