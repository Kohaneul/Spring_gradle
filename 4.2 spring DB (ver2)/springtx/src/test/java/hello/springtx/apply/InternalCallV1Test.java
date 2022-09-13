package hello.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Calls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
@Slf4j
@SpringBootTest
public class InternalCallV1Test {
    @Autowired CallService callService;

    @Test
    void printProxy(){
        log.info("callService class={}",callService.getClass());
    }

    @Test
    void internalCall(){
        callService.internal(); //트랜잭션 적용 후 호출
        log.info("callService class={}",this.getClass());

    }
    @Test
    void externalCall(){
        callService.external();
        log.info("callService class={}",this.getClass());
        //external 안에 있는 internal 메소드는 트랜잭션 적용되지 않게된다.
        //이유 : external 메소드는 트랜잭션 적용되지 않으며 프록시 객체가 아닌 타겟 객체가 호출된다
        //자바언어에서 별도의 참조가 없으면 this라는 뜻으로 자신의 인스턴스를 가르킨다.
        //internal 메소드 또한 프록시 객체를 호출해야하는데 이 타겟 객체의 this.internal 메소드가 호출하기 때문
    }

    @TestConfiguration
    static class InternalCallV1TestConfig{
        @Bean
        public CallService callService(){
            return new CallService();
        }


    }
    @Slf4j
    static class CallService{

        public void external(){
            log.info("call external");
            printTxInfo();
            internal();
        }

        @Transactional
        public void internal(){
            log.info("call internal");
            printTxInfo();
        }
        private void printTxInfo(){
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive={}",txActive);


        }

    }


}
