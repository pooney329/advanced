package com.example.proxy.app.v1;

import com.example.proxy.util.CommonUtils;

public class OrderRepositoryV1Impl implements OrderRepositoryV1{
    @Override
    public void save(String itemId) {
        //저장 로직
        if(itemId.equals("ex")){
            throw new IllegalStateException("예외 발생!");
        }
        CommonUtils.sleep(1000);

    }
}