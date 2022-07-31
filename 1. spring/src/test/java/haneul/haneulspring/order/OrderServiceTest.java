package haneul.haneulspring.order;

import haneul.haneulspring.AppConfig;
import haneul.haneulspring.discount.FixDiscountPolicy;
import haneul.haneulspring.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    AppConfig appConfig = new AppConfig();

    MemberService memberService;
    OrderService orderService;

    @Test
    void createOrder(){
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();

        Member memberA = new Member(1L,"memberA",Grade.VIP);
        memberService.join(memberA);
        Order o = orderService.createOrder(memberA.getId(),"a",10000);

        Assertions.assertThat(o.getMemberId()).isEqualTo(memberA.getId());
    }




//
//    @Test
//    void fieldInjectionTest(){
//        OrderServiceImpl orderService1 = new OrderServiceImpl();
//        orderService1.createOrder(1L,"itemA",10000);
//
//    }
}
