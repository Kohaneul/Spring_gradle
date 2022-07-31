package haneul.haneulspring.member;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;//추상화에만 의존함,

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;   //생성자 주입
    }
    //Component를 쓰게 되면 이 클래스 내에서 의존관계 주입 설정이 어렵기 때문에 자동으로 Autowired를 사용하게 된다.
    //그러면 type을 찾아서 주입하게 된다. MemberRepository -> MemoryMemberRepository

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
