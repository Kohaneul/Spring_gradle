package hello.proxy.config.v2_dynamic_proxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
@RequiredArgsConstructor
public class LogTraceBasicHandler implements InvocationHandler{
    private final Object target;
    private final LogTrace logTrace;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
