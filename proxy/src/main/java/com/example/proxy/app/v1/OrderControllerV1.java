package com.example.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

/**
 * @RequestMapping //스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
 * @ResponseBody  --> 스프링 부트 3.0 이상 부터는 @RequestMapping만으로는 컨트롤러로 인식하지 못해
 * @Controller 또는 @RestController를 사용하여 컨트롤러를 명확히 지정해야 한다.
 */

@RestController
public interface OrderControllerV1 {

    /**
     * Interface에서는 {@link @RequestParam}와 같이 어노테이션을  생략하면 안된다.
     * 클래스 레벨에서는 가능하지만 Interface에서는 인식 하지 못해 오류가난다.
     * @param itemId
     * @return
     */
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
