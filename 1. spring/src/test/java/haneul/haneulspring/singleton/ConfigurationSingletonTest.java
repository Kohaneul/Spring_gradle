package haneul.haneulspring.singleton;

import haneul.haneulspring.AppConfig;
import haneul.haneulspring.member.MemberRepository;
import haneul.haneulspring.member.MemberService;
import haneul.haneulspring.member.MemberServiceImpl;
import haneul.haneulspring.member.MemoryMemberRepository;
import haneul.haneulspring.order.OrderService;
import haneul.haneulspring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest(){


        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        System.out.println("memberService->MemberRepository : "+memberRepository1);
        System.out.println("orderService->MemberRepository : "+memberRepository2);
        System.out.println("memberRepository : "+memberRepository);
        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2).isSameAs(memberRepository);



    }


    @Test
    void configurationDeep(){


        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = "+bean.getClass());

        /*
        * 스프링에서 @CGLIB이라는 기술을 이용하여 조작-> AppConfig에 상속받게 한다. -> 싱글톤 보장
        * 그러므로 AppConfig가 호출될때 자식클래스인 AppConfig@CGLIB이 호출됨
        *
        * @Configuration으로 적용하지 않고 그냥 @Bean만 적용하게 된다면?
        *  스프링 빈은 IOC 컨테이너에 등록 되나, 싱글톤이 깨짐.
        * */
    }

}
