package haneul.haneulspring;

import haneul.haneulspring.discount.DiscountPolicy;
import haneul.haneulspring.discount.FixDiscountPolicy;
import haneul.haneulspring.discount.RateDiscountPolicy;
import haneul.haneulspring.member.MemberRepository;
import haneul.haneulspring.member.MemberService;
import haneul.haneulspring.member.MemberServiceImpl;
import haneul.haneulspring.member.MemoryMemberRepository;
import haneul.haneulspring.order.Order;
import haneul.haneulspring.order.OrderService;
import haneul.haneulspring.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    //팩토리 빈(AppConfig)를 통해서 등록하는 방법

    //AppConfig가 제공하는 메서드 1 : memberService
    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
       return new OrderServiceImpl(memberRepository(), discountPolicy());
    //    return null;
    }
    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }




}
