package com.example.proxy.pureproxy.concreteproxy.code;

public class ConcreteClient {
    /**
     * 클래스 기반 프록시
     *  - 다형성에 의해 ConcreteLogic ,TimeProxy 모두 주입 가능
     *  - 자바 언어에서는 다형성은 인터페이스나 클래스를 구분하지 않고 모두 적용된다.
     */

    private final ConcreteLogic concreteLogic;

    public ConcreteClient(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    public void execute(){
        concreteLogic.operation();
    }
}
