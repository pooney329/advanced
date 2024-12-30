package com.example.proxy.app.v3;

import com.example.proxy.util.CommonUtils;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV3 {
    public void save(String itemId) {
        //저장 로직
        if(itemId.equals("ex")){
            throw new IllegalStateException("예외 발생!");
        }
        CommonUtils.sleep(1000);

    }
}
