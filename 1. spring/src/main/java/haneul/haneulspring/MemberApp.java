package haneul.haneulspring;

import haneul.haneulspring.member.*;
import haneul.haneulspring.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;

public class MemberApp {
    public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        //AppConfig에 있는 환경설정 정보를 가지고 스프링 Bean에 관리함

    Member member = new Member(1L,"memberA",Grade.VIP);
    memberService.join(member);
    Member findMember = memberService.findMember(member.getId());
    System.out.println("new member = "+member.getName());

    }
}
