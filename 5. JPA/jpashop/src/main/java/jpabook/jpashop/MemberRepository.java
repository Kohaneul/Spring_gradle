package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    //PersistenceContext 어노테이션이 있으면, 스프링 부트가 엔티티 매니저 주입해줌.

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public Member findById(Long id){
        return em.find(Member.class,id);
    }
}
