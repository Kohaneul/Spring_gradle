package hello.proxy.config.v2_dynamic_proxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler{
    //특정 조건일때는 안하도록 패턴 적용
    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns;    //어떤 패턴일때만 로그 남김
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();

        //save,request, reque*,*est
        if(!PatternMatchUtils.simpleMatch(patterns,methodName)){
            //패턴 매칭이 안되면 실제 로직 바로 호출
            return method.invoke(target,args);
        }

        TraceStatus status = null;
        try{
            String message = method.getDeclaringClass().getSimpleName()+"."+method.getName()+"()";
            //로직호출
            status = logTrace.begin(message);
            Object result = method.invoke(target, args);
            logTrace.end(status);
            return result;
        }
        catch(Exception e){
            logTrace.exception(status,e);
            throw e;
        }

    }
}
