package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.PostConstruct;

@SpringBootTest
public class initTxTest {

    @Autowired Hello hello;

    @Test
    void go(){
        //초기화 코드는 스프링이 초기화 시점에 호출.

    }
    @TestConfiguration
    static class InitTxTextConfig{
        @Bean
        Hello hello(){
            return new Hello();
        }
    }
    @Slf4j
    static class Hello{
        @PostConstruct
        @Transactional
        public void initV1(){
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init @PostConstruct tx Active={}",isActive);
        }
        @EventListener(ApplicationReadyEvent.class) //스프링 컨테이너가 다 뜨면 호출
        @Transactional
        public void initV2(){
            boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("Hello init2 @EventListener tx active={}",isActive);
        }
    }

}
