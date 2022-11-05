package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 > 성능 최적화
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateMember(member.getName());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(String name) {
        //중복회원이 있으면 예외 발생
        List<Member> findMembers = memberRepository.findByName(name);
            if(!findMembers.isEmpty()){
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }    }

    public Member findOne(Long id){
       return memberRepository.findOne(id);
    }

    //회원 전체 조회
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public List<Member> findByName(String name){
        return memberRepository.findByName(name);
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

}
