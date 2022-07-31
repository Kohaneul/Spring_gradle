package haneul.haneulspring.member;

import haneul.haneulspring.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;
    @BeforeEach //Test실행 전 무조건 실행
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }
    @Test
    void join(){
        //given : 주어졌을
        Member member = new Member(1L, "memberA",Grade.VIP);
        //when :때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
        //then :이렇게 한다
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
