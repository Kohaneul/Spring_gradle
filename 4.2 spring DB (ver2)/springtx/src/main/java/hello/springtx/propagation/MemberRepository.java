package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public void save(Member member){
        log.info("멤버 저장!");
        em.persist(member);
        log.info("tx1 종료!");


    }

    public void save1(Member member){
        em.persist(member);
        log.info("멤버 저장!");

    }


    public Optional<Member> findByName(String userName){
        return em.createQuery("select m from Member m where m.username = :username",Member.class)
                .setParameter("username",userName).getResultList().stream().findAny();
    }



}
