package com.example.advanced.trace.logtrace;

import com.example.advanced.trace.TraceId;
import com.example.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

/**
 *  TraceId를 파라미터로 전달 하는 방식이 아닌 holder를 사용하여 저장해서 사용하는 방식 사용
 */
@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생!

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * TraceId를 새로 생성 하거나 Level을 증가 시켜 holder에 저장
     */
    private void syncTraceId(){
        if(traceIdHolder == null){
            traceIdHolder = new TraceId();
        }
        else{
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status,e);
    }

    /**
     * 요청의 흐름을 한곳에서 편리하게 처린한다. 실행 시간을 측정한고 로그를 남긴다
     *
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

        releaseTraceId();
    }

    /**
     * 메소드 호출이 끝나면 Level을 감소 시키는 역할
     * 단 Level이 1인 경우에는 제거
     */
    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel()){
            traceIdHolder = null; //destroy
        }
        else{
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    /**
     * level 0 :
     * level 1 : |-->
     * level 2 : |   |-->
     * <p>
     * level 0 :
     * level 1 : |<--
     * level 2 : |   |<--
     * <p>
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
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
