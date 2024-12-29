package com.example.advanced.trace.hellotrace;

import com.example.advanced.trace.TraceId;
import com.example.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";


    /**
     * 로그를 시작한다
     * 로그 메시지를 파라미터로 받아서 시작 로그를 출력한다
     * 응답 결과로 현재 로그의 상태인 TraceStatus를 반환한다.
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * 로그를 정상 종료한다
     * @param status
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 로그를 예외 상황으로 종료한다
     * @param status
     * @param e
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    /**
     * 요청의 흐름을 한곳에서 편리하게 처린한다. 실행 시간을 측정한고 로그를 남긴다
     * @param status
     * @param e
     */
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    /**
     * level 0 :
     * level 1 : |-->
     * level 2 : |   |-->
     *
     * level 0 :
     * level 1 : |<--
     * level 2 : |   |<--
     *
     * level 0 :
     * level 1 : |<x-
     * level 2 : |  |<x-
     *
     * @param prefix
     * @param level
     * @return
     */
    private Object addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<level; i++){
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
