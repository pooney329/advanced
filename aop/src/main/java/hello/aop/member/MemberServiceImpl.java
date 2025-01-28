package hello.aop.member;

import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component //Aop를 사용하기 위해서는 Bean으로 만들어 져야한다.
public class MemberServiceImpl implements MemberService{

    @MethodAop("test value")
    @Override
    public String hello(String name) {
        return "ok";
    }

    public String internal(String param){
        return "ok";
    }
}
