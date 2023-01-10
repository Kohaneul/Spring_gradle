package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LogRepository {

    private final EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Log logMessage){
        em.persist(logMessage);
        log.info("로그 저장");

        if(logMessage.getMessage().contains("로그예외")){
            log.info("log 저장시 예외 발생");
            throw new RuntimeException("예외 발생");    //runEx 발생시 rollback
        }

        log.info("tx2 종료!");


    }

    public void save1(Log logMessage){
        em.persist(logMessage);
        log.info("로그 저장");
        if(logMessage.getMessage().contains("로그예외")){
            log.info("log 저장시 예외 발생");
            throw new RuntimeException("예외 발생");    //runEx 발생시 rollback
        }

    }
    public Optional<Log> findByMessage(String message){
        return em.createQuery("select m from Log m where m.message = :message", Log.class).setParameter("message",message)
                .getResultList().stream().findAny();
    }

}
