package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {
    /**
     * JDK Proxy는 MemberService
     * 인터페이스를 기반으로 생성된 프록시이다. 따라서 JDK Proxy는 MemberService 로 캐스팅은 가능하지만
     * MemberServiceImpl 이 어떤 것인지 전혀 알지 못한다. 따라서 MemberServiceImpl 타입으로는 캐스팅이 불
     * 가능하다. 캐스팅을 시도하면 ClassCastException.class 예외가 발생한다.
     */
    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // jdk 동적 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        //JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패 , ClassCastException 예외 발생
        Assertions.assertThrows(ClassCastException.class, () -> {MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;});

    }

    /**
     * CGLIB Proxy를 대상 클래스인 MemberServiceImpl 타입으로 캐스팅하면 성공한다.
     * 왜냐하면 CGLIB는 구체 클래스를 기반으로 프록시를 생성하기 때문이다. CGLIB Proxy는 MemberServiceImpl
     * 구체 클래스를 기반으로 생성된 프록시이다. 따라서 CGLIB Proxy는 MemberServiceImpl 은 물론이고,
     * MemberServiceImpl 이 구현한 인터페이스인 MemberService 로도 캐스팅 할 수 있다.
     */
    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // cllib 프록시

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());

        //CGLIB 동적 프록시를 구현 클래스로 캐스팅 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }
}
