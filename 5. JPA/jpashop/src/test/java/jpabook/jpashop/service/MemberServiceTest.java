package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;



    //회원가입

    // @Rollback(value = false) : 스프링에서 Transactional 은 테스트케이스에 있으면 기본적으로 커밋이 아닌 롤백을 해버리기 때문에 insert 쿼리문이 나가지 않음
    //직접 확인하기 위해서 Rollback에서 설정해줌
    @Test
    public void 회원가입(){
        //given
        Member member = new Member("kim");
        member.setAddress(new Address("seoul","gangnam gu","322"));
        //when
        Long savedId = memberService.join(member);
        //then
        Assert.assertEquals(member,memberRepository.findOne(savedId));
    }

    //중복 회원 예외
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_가입() throws Exception {
        //given
        Member member1 = new Member("kim");

        Member member2= new Member("kim");
        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        Assertions.fail("예외가 발생해야 한다.");
    }





}