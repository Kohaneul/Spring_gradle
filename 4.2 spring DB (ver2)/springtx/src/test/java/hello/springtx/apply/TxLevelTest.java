package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
public class TxLevelTest {
    @Autowired LevelService service;

    @Test
    void orderTest(){
        service.write();
        service.read();
        Assertions.assertThat(AopUtils.isAopProxy(service)).isTrue();
    }

    @TestConfiguration
    static class TxLevelTestConfig{
        @Bean
        LevelService levelService(){
            return new LevelService();
        }
    }

    @Slf4j
    @Transactional(readOnly = true) //readOnly : 읽기 전용 트랜잭션(쓰기작업x)
    static class LevelService{

        @Transactional(readOnly = false)    //default: false
        public void write(){
            log.info("call write");
            printTxInfo();
        }

        public void read(){
            log.info("call read");
            printTxInfo();
        }

        private void printTxInfo(){
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}",txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();//현재 트랜잭션이 ReadOnly인가?
            log.info("tx readOnly={}",readOnly);
        }
    }
}
