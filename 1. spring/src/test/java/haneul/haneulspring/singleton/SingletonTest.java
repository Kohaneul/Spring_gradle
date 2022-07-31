package haneul.haneulspring.singleton;

import haneul.haneulspring.AppConfig;
import haneul.haneulspring.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        //1. 조회 : 호출할때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회 : 호출될때마다 객체 생성
        MemberService memberService2 = appConfig.memberService();

        //참조값이 다른것을 확인
       System.out.println(memberService1);
        System.out.println(memberService2);

        //멤버서비스1, 멤버서비스2와 다른 객체인걸 확인하는 test
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService instance1= SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        System.out.println("1 : "+instance1);
        System.out.println("2 : "+instance2);

        assertThat(instance1).isSameAs(instance2);
        //same 객체 인스턴스 맞는지 비교
        //equal 인스턴스 비유
    }


    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
   //     AppConfig appConfig = new AppConfig();
       ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        //1. 조회 : 호출할때마다 객체 생성
        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        //2. 조회 : 호출될때마다 객체 생성
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);

        //참조값이 다른것을 확인
        System.out.println(memberService1);
        System.out.println(memberService2);

        //멤버서비스1, 멤버서비스2와 다른 객체인걸 확인하는 test
        assertThat(memberService1).isSameAs(memberService2);
    }



}
