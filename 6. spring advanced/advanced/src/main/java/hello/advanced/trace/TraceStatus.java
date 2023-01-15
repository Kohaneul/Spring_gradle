package hello.advanced.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraceStatus {  //로그 시작시 상태정보를 담고 있는 클래스

    private TraceId traceId;
    private Long startTimeMs;   //로그 시작 시간
    private String message;

}
