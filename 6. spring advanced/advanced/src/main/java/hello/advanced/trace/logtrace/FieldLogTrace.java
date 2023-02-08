package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X--";

    private TraceId traceIdHolder;  // ** traceId 동기화 , 동시성 이슈 발생

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();  //해당 메서드를 통해서 holder 에 값이 들어가있는게 보장됨.
        long startTimeMs = System.currentTimeMillis();
        //로그 출력
        log.info("[{}] {}{} time={}ms",traceIdHolder.getId(),addSpace(START_PREFIX,traceIdHolder.getLevel()),message,startTimeMs);
        return new TraceStatus(traceIdHolder,startTimeMs,message);
    }

    private void syncTraceId(){
        if(traceIdHolder==null){    //최초호출이면 새로만든다.
            traceIdHolder = new TraceId();
            log.info("새로만든다");
        }
        else{   //최초호출이 아니면 동기화
            traceIdHolder = traceIdHolder.createNextId();

        }
    }

    private String addSpace(String prefix,int level){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<level;i++){
            sb.append((i==level-1)?"|"+prefix:"|  ");
        }
        return sb.toString();
    }

    @Override
    public void end(TraceStatus traceStatus) {
        complete(traceStatus,null);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if(e==null){
            log.info("[{}] {}{} time={}ms",traceId.getId(),addSpace(COMPLETE_PREFIX,traceId.getLevel()),status.getMessage(),resultTimeMs);
        }
        else{
            log.info("[{}] {}{} time={}ms ex={}",traceId.getId(),addSpace(EX_PREFIX,traceId.getLevel()),status.getMessage(),resultTimeMs,e.toString());
        }
        releaseTraceId();
    }

    private void releaseTraceId() {
    if(traceIdHolder.isFirstLevel()){
        traceIdHolder = null;   //destroy
    }
    else{   //중간단계일때
        traceIdHolder = traceIdHolder.createPreviousId();
    }

    }


    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status,e);

    }
}