package com.example.proxy.trace.callback;

import com.example.proxy.trace.TraceStatus;
import com.example.proxy.trace.logtrace.LogTrace;

/**
 * 템플릿 콜백 패턴 적용
 * {@link TraceTemplate} 은 템플릿 역할 수행  {@link TraceCallback}은 콜백 역할 수행
 */
public class TraceTemplate {


    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            //로직 호출
            T result = callback.call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져주어야 한다.
        }
    }


}
