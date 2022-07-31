package haneul.haneulspring;

import haneul.haneulspring.member.Grade;
import haneul.haneulspring.member.Member;
import haneul.haneulspring.member.MemberService;
import haneul.haneulspring.order.Order;
import haneul.haneulspring.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    MemberService memberService = appConfig.memberService();
//    OrderService orderService = appConfig.orderService();



        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService",MemberService.class);
        OrderService orderService = ac.getBean("orderService",OrderService.class);
    Member memberA = new Member(1L,"memberA",Grade.VIP);
    memberService.join(memberA);
    Order order  =orderService.createOrder(memberA.getId(),"a",30000);
    System.out.println(order);
    }
}
