package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
@Slf4j
@Aspect
public class AspectV2 {

    // hello.aop.order 패키지와 하위패키지 모두 포함
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){}   //Pointcut signature

    @Around("allOrder()")
    public Object noLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}",joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
