package hello.springtx.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired RollbackService rollbackService;

    @Test
    void runtimeEx(){
        Assertions.assertThatThrownBy(()-> rollbackService.runtimeException())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void checkedException(){
        Assertions.assertThatThrownBy(()-> rollbackService.checkedException())
                .isInstanceOf(MyException.class);
    }

    @Test
    void checkedExceptionRollBack(){
        Assertions.assertThatThrownBy(()->rollbackService.rollbackFor()).isInstanceOf(MyException.class);
    }



    @TestConfiguration
    static class RollbackTestConfig{
        @Bean
        RollbackService rollbackService(){
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService{
        //런타임예외 발생 : 롤백
        @Transactional
        public void runtimeException(){
            log.info("call runtimeException");
            throw new RuntimeException();   //언체크(런타임Ex)의 자식들은 다 롤백됨
        }

        //체크예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyException {
            log.info("call checkedException");
            throw new MyException();    //체크예외 > 커밋
        }

        //체크예외 발생 : rollbackFor 지정 : 강제롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            log.info("call rollbackFor");
            throw new MyException();
        }



    }


    static class MyException extends Exception{

    }
}
