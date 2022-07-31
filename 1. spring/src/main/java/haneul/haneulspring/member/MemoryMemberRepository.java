package haneul.haneulspring.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component  //자동 bean 등록시, memoryMemberRepository로 등록 됨
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    @Override
    public void save(Member member) {
        store.put(member.getId(),member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
