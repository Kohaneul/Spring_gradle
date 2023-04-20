package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect //Annotation 기반 프록시 적용시 필요
@RequiredArgsConstructor
public class LogTraceAspect {
    private final LogTrace logTrace;

//    @Around("execution(* hello.proxy.app..*(..))")  //pointcut
    @Around("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //advice(부가기능 로직)
        TraceStatus status = null;
        try{
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            //로직 호출
            Object result = joinPoint.proceed();    //실제 호출 대상 호출

            logTrace.end(status);
            return result;
        }
        catch(Exception e){
            logTrace.exception(status,e);
            throw e;
        }
    }

}
