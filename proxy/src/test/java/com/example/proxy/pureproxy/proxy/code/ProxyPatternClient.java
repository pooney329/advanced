package com.example.proxy.pureproxy.proxy.code;

/**
 * 프록시 패턴
 * 원래 서버가  제공하는 기능에 접근 제어를 한다.
 */
public class ProxyPatternClient {
    private Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute(){
        subject.operation();
    }
}
