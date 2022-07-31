package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;



class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstnace();
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();//테스트가 끝나면 무조건 실행된다.
    }

    @Test
    @DisplayName("실험")
    void save(){
        //given(~가 주어졌을때)
        Member member = new Member("hello",20);
        //when(~를 실행했을떄)
        Member saveMember = memberRepository.save(member);
        //then(결과가 ~~이어야 해)
        Member findMember = memberRepository.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);
    }
    @Test
    @DisplayName("test2")

    void findAll(){
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);
        Member member3 = new Member("member3", 40);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        //when
        List<Member> result = memberRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(3);
       assertThat(result).contains(member1,member2,member3);
    }
}