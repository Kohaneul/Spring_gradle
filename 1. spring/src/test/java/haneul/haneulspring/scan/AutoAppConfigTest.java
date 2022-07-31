package haneul.haneulspring.scan;

import haneul.haneulspring.AutoAppConfig;
import haneul.haneulspring.member.MemberRepository;
import haneul.haneulspring.member.MemberService;
import haneul.haneulspring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
  @Test
    void basicScan(){
      AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
      MemberService memberService = ac.getBean(MemberService.class);
      Assertions.assertThat(memberService.getClass());
    OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
    MemberRepository memberRepository = bean.getMemberRepository();
    System.out.println(memberRepository);

  }
}
