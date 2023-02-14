package hello.advanced.trace.callback;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TraceTemplate {
    private final LogTrace logTrace;

    public<T> T execute(String message, TraceCallBack<T> callback){
        TraceStatus status = null;
        try{
            status = logTrace.begin(message);

            //로직 호출
            T call = callback.call();

            logTrace.end(status);
            return call;
        }
        catch(Exception e){
            logTrace.exception(status,e);
            throw e;
        }

    }

}
