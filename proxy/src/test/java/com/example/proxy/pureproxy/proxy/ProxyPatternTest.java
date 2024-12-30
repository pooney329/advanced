package com.example.proxy.pureproxy.proxy;

import com.example.proxy.pureproxy.proxy.code.CacheProxy;
import com.example.proxy.pureproxy.proxy.code.ProxyPatternClient;
import com.example.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {
    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    /**
     * Proxy 패턴을 이용한 캐싱
     */
    @Test
    void cacheProxtTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
