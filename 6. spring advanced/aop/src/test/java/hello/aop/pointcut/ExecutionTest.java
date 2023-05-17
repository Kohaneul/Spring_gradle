package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ExecutionTest {
   AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
   Method helloMethod;

   @BeforeEach
    public void init() throws NoSuchMethodException {
       helloMethod = MemberServiceImpl.class.getMethod("hello",String.class);
   }

   @Test
    void printMethod(){
       //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
       log.info("helloMethod={}",helloMethod);
   }


   @Test
    void exactMatch(){
       //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
       assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
   }

   @Test
    void allMatch(){
       pointcut.setExpression("execution(* *(..))");
       assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
   }

   @Test
    void nameMatch1(){
       pointcut.setExpression("execution(* hel*(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
   }
    @Test
    void nameMatch2(){
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse(){
      pointcut.setExpression("execution(* *nono*(..))");
      assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1(){
       pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){
       pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatchFalse(){
       pointcut.setExpression("execution(* hello.aop.*.*(..))");    //hello.aop 패키지에 맞아야함
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1(){
       pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2(){
       pointcut.setExpression("execution(* hello.aop..*.*(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeExactMatch(){
       pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeExactSuperType(){
       pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
       //MemberService =>MemberServiceImpl 의 부모타입으로 매칭시에도 성공
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
    }
    @Test
    void typeMatchInternal() throws NoSuchMethodException {
       pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(internalMethod,MemberServiceImpl.class)).isTrue();
    }


    @Test
    void typeMatchInternalNoSuperTypeMethodFalse() throws NoSuchMethodException {
       pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        //internal => 자식타입에 있는 다른 메서드까지 매칭이 되는것인가..??
        assertThat(pointcut.matches(internalMethod,MemberServiceImpl.class)).isFalse();
        //부모타입(인터페이스)에 선언된 메서드까지 매칭
   }

   //String 타입의 파라미터 허용
   @Test
    void argsMatch(){
       pointcut.setExpression("execution(* *(String))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
   }
   //파라미터가 없어야 함
   @Test
    void argsMatchNoArgs(){
       pointcut.setExpression("execution(* *())");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isFalse();
   }

   //정확히 하나의 파라미터 허용, 모든 타입 허용
   @Test
    void argsMatchStar(){
       pointcut.setExpression("execution(* *(*))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
   }

   //파라미터 갯수와 무관하게 모든 파라미터, 모든 타입 허용
    //() (Xxx), (Xxx,xx)
   @Test
    void argsMatchAll(){
       pointcut.setExpression("execution(* *(..))");
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
   }

    //String 타입으로 시작 ,파라미터 갯수와 무관하게 모든 파라미터, 모든 타입 허용
    //(String), (String, Xxx), (String, Xxx,xx)
   @Test
    void argsMatchComplex(){
       pointcut.setExpression("execution(* *(String, ..))");
       // 파라미터 String, String -> (String,String)
       //파라미터 두개인데 처음엔 무조건 String -> (String, *)
       assertThat(pointcut.matches(helloMethod,MemberServiceImpl.class)).isTrue();
   }

}