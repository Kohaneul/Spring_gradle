package hello.aop.order;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
/**
 * application.properties
 * spring.aop.proxy-target-class-true : CGLIB
 * spring.aop.proxy-target-class=false : JDK 동적 프록시
 * */
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") //JDK 동적 프록시
@SpringBootTest // DEFAULT : PROXY 타겟클래스 기반으로 만듦... CGLIB 프록시
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {
    @Autowired
    MemberService memberService;


    @Test
    void success(){
        log.info("memberService Proxy={}",memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    @Slf4j
    static class ThisTargetAspect{
        //부모타입 허용
        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}",joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface]{}",joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl]{}",joinPoint.getSignature());
            return joinPoint.proceed();
            //JDK 동적 프록시일 경우 Advice 적용 안됨 -> MemberService 만 등록하기 때문에 구현체에 대해서는 모름
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl]{}",joinPoint.getSignature());
            return joinPoint.proceed();
        }

    }





}
