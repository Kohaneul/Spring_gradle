package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence=0L;

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save : member={}",member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        Member member = store.get(id);
        return member;
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId){
//        List<Member> list = findAll();
//        for (Member member : list) {
//            if(member.getLoginId().equals(loginId)){
//                return Optional.of(member);
//            }
//        }
//        return Optional.empty();
        return findAll().stream().filter(member-> member.getLoginId().equals(loginId)).findFirst();
        //filter: 만족하는 값만 넘어감. findFirst : 먼저 나오는 값을 반환
    }

}
