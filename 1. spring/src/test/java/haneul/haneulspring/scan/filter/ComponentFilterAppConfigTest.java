package haneul.haneulspring.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {
    @Test
    void filterScan(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        System.out.println("beanA : "+beanA);
        assertThat(beanA).isNotNull();

        assertThrows(
                NoSuchBeanDefinitionException.class,
                ()->ac.getBean("beanB",BeanB.class));
    }
    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type= FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            //ANNOTATION으로 MyIncludeComponent라고 붙은 클래스는 ComponentScan에서 포함하겠다
            excludeFilters = @Filter(type = FilterType.ANNOTATION,classes = MyExcludeComponent.class)
            //ANNOTATION으로 MyExcludeComponent라고 붙은 클래스는 ComponentScan에서 제외하겠다
    )


    /*
    *만약 MyIncludeComponent도 ComponentScan에서 제외할 경우,
    * excludeFilters에 추가해 주면 된다
    *   excludeFilters = {@Filter(type=FilterType.ANNOTATION,classes=MyExcludeComponent.class),
    *   @Filter(type=FilterType.ANNOTATION,classes=MyIncludeComponent.class)};
    *
    * */
    static class ComponentFilterAppConfig{

    }
}
