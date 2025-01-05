package com.example.proxy.trace;


public class TraceStatus {
    private TraceId traceId; //내부 트랜잭션Id, Level을 가지고 있다.
    private long startTimeMs; // 로그 시작시간
    private String message; // 시작시 사용한 메시지


    public TraceStatus(TraceId traceId, long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
