package haneul.haneulspring.autowired;

import haneul.haneulspring.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {
    @Test
    void AutoWiredOption(){
         ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{
    @Autowired(required = false) //required의 default는 true
        public void setNoBean1(Member noBean1) {
        System.out.println("noBean1 : "+noBean1);
        }

        //Member은 스프링컨테이너가 관리하는 Bean이 아님(Test를 위해 주입)
        //setNoBean1은 호출이 안됨 ==> Autowired에 required false를 하게 된다면 의존관계가 없어서 메서드 자체가 호출되지 않음

        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            //Member은 스프링컨테이너가 관리하는 Bean이 아님(Test를 위해 주입)
            System.out.println("noBean2 : "+noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            //Member은 스프링컨테이너가 관리하는 Bean이 아님(Test를 위해 주입)
            System.out.println("noBean3 : "+noBean3);
        }

    }

    }





