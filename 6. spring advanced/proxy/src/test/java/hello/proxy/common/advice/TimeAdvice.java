package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        //타겟 클래스의 정보가 MethodInvocation 에 있음
        long startTimeMs = System.currentTimeMillis();

        Object result = invocation.proceed();
        //invocation.proceed를 하면 알아서 target을 찾아서 인수 넘기면서 실행해줌...
        long resultMs=System.currentTimeMillis()-startTimeMs;
        log.info("TimeProxy 종료 resultTime={}",resultMs);

        return result;
    }
}
