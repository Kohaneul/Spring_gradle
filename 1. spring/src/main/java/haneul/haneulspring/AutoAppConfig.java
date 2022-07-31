package haneul.haneulspring;

import haneul.haneulspring.discount.DiscountPolicy;
import haneul.haneulspring.member.MemberRepository;
import haneul.haneulspring.member.MemoryMemberRepository;
import haneul.haneulspring.order.OrderService;
import haneul.haneulspring.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

    @Configuration //스프링 빈 설정 클래스. 자동으로 싱글톤으로 만들어준다.
    @ComponentScan( //ComponentScan  :Annotation이 붙은 클래스들을 자동으로 bean으로 등록
     //   basePackages = "haneul.haneulspring.member",    //어디서부터 찾는지 위치 지정. 여러개 지정 가능
      //      basePackageClasses = AutoAppConfig.class, //지정한 위치의 클래스를 탐색 위치로 지정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
            //만약 basepackage를 지정 안한다면? AutoAppConfig가 속한 모든 패키지를 다 조회함.
)
public class AutoAppConfig{
//
//    @Bean
//        OrderService orderService(){
//        return new OrderServiceImpl();
//    }
//    @Bean(name="memoryMemberRepository")
//        MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
    /*  이 경우 수동으로 등록한 @Bean이름 : memoryMemberRepository와 Component를 이용해
        자동 등록된 MemoryMemberRepository(bean 이름 : memoryMemberRepository)와 이름이 충돌하게 된다.
        하지만 수동 Bean이 우선권을 가지게 되며 자동 Bean을 오버라이딩 해준다.
    */
}