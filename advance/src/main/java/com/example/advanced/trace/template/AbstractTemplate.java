package com.example.advanced.trace.template;

import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.logtrace.LogTrace;

/**
 * 템플릿 메소드 패턴
 * - 템플릿(공통적으로 사용)을 사용하는 방식으로 변하지 않는 부분을 모아두고, 일부 변하는 부분을 별도 호출해서 해결 하는 패턴
 * - 해당 클래스는 부모 클래스이고 템플릿 역할을 한다.
 */
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }


    //변하지 않는 부분
    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            //로직 호출
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }
    }

    //변하는 부분 (상속으로 구현)
    protected abstract T call();

}
