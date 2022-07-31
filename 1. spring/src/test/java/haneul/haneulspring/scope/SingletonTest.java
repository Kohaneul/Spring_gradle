package haneul.haneulspring.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PreDestroy;

public class SingletonTest {
    @Test
    void singletonBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        System.out.println(singletonBean1);
        System.out.println(singletonBean2);
        Assertions.assertThat(singletonBean1).isEqualTo(singletonBean2);
        ac.close();
    }

    @Scope("singleton")//싱글톤 : 스프링 컨테이너가 생성부터 소멸까지 관리
    static class SingletonBean{
        public void init(){
            System.out.println("Singleton.init");
        }
        @PreDestroy
        public void destroy(){
            System.out.println("Singleton.Destroy");
        }

    }
}
