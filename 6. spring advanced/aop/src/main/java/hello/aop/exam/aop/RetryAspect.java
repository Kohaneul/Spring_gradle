package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class RetryAspect {
    @Around("@annotation(retry)")         //재시도할때 joinpoint 호출 해야하기 떄문에 around
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {  //어노테이션으로 지정한 Retry 가 파라미터로 받으면 대체됨
       log.info("[retry] {} retry={}",joinPoint.getSignature(),retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;
        for(int retryCount = 1; retryCount<=maxRetry;retryCount++) {
            //예외 발생하게되면
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed(); //2. 호출
            } catch (Exception e) {
                exceptionHolder =  e;   //1. 잡아서 예외 넣어놓고
            }
        }
        throw exceptionHolder;  //3번 넘어가면 예외 호출
    }
}
