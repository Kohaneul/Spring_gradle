package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {
    @Test
    void basicConfig(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);
        //A는 bean으로 등록된다
//        A a = ac.getBean("beanA", A.class);
//        a.helloA();   오류발생 이유는 빈 후처리기에서 bean 등록 후 B 객체로 바꿔치기 했기 때문..
        B a = ac.getBean("beanA",B.class);  //이름만 beanA이고 실제 객체는 B객체임
        a.helloB();
        //B는 빈으로 등록되지 않는다.
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()->ac.getBean(A.class));

    }
    @Slf4j
    static class A{
        public void helloA(){log.info("helloA");}
    }

    @Slf4j
    static class B{
        public void helloB(){log.info("helloB");}}

    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor{

        @Override   //bean 초기화 후 호출되는 빈후처리기
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}",beanName,bean);  //스프링컨테이너가 bean이름, 객체 전달해줌
            if(bean instanceof A){  //A가 들어오면 B로 바꿈
                return new B();
            }
            return bean;    //원래 들어왔던 스프링 빈이면 그냥 등록
        }
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig{

        @Bean(name="beanA")
        public A a(){return new A();}
        @Bean
        public AToBPostProcessor helloPostProcessor(){
            return new AToBPostProcessor();
        }
    }
}
