package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Autowired BasicService basicService;

    @Test
    void proxyCheck(){
        log.info("aop class={}",basicService.getClass());
        assertThat(AopUtils.isAopProxy(basicService)).isTrue();  //스프링 aop 프록시로 적용이 되었는지 확인.
    }

    @Test
    @DisplayName("tx 테스트")
    void txTest(){
        basicService.tx();
        basicService.nonTx();
    }

    @TestConfiguration
    static class TxApplyBasicConfig{
        @Bean
        BasicService basicService(){
            return new BasicService();
        }
    }
    @Slf4j
    static class BasicService{
        @Transactional
        public void tx(){
            //클라이언트가 해당 메서드를 호출할때 프록시의 tx 메서드 호출
            // 로그
            // Getting transaction for [hello.springtx.apply.TxBasicTest$BasicService.tx]
            // Completing transaction for [hello.springtx.apply.TxBasicTest$BasicService.tx]
            log.info("call tx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();   //트랜잭션이 활성화되었는지 확인
            log.info("tx active={}",txActive);

        }
        public void nonTx(){    //트랜잭션 관련 로직을 호출하지 않고 실제 nonTx를 호출하고 끝
            log.info("call nonTx");
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}",txActive);
        }
    }
}
