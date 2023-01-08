package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {
    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config{

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit(){
        log.info("트랜잭션 시작!");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작!");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료!");

    }


    @Test
    void rollBack(){
       log.info("트랜잭션 시작!");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션 롤백 시작!");
        txManager.rollback(status);
       log.info("트랜잭션 롤백 완료!");

    }

    @Test
    void double_commit(){
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋!");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 커밋!");
        txManager.commit(tx2);
    }

    @Test
    void double_commit_rollback(){
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션1 커밋");
        txManager.commit(tx1);

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("트랜잭션2 롤백");
        txManager.rollback(tx2);
   }

    @Test
    void outer_commit(){
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}",outer.isNewTransaction());

        //내부트랜잭션은 실행되지 않음 -> 한번 커밋해버리면 끝나기 때문에 해당 내부 트랜잭션이 커밋되어 버리면
        // 외부 트랜잭션은 수행하지 못함. 그러므로 해당 내부 트랜잭션은 아무것도 하지 않고 외부 트랜잭션에만 참여함
        innerTx();

        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);    //실제 이 시점에서 커밋이 실행됨.

    }

    private void innerTx() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}",inner.isNewTransaction());

        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);
    }

    @Test
    void outer_rollback(){
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}",outer.isNewTransaction());

        //내부트랜잭션은 실행되지 않음 -> 한번 커밋 or 롤백 되어버리면 끝나기 때문에 내부 트랜잭션이 커밋되어 버리면
        // 외부 트랜잭션은 수행하지 못함. 내부 트랜잭션은 아무것도 하지 않고 외부 트랜잭션에만 참여함
        inner_commit();

        log.info("외부 트랜잭션 롤백");
        txManager.rollback(outer);
        //내부 트랜잭션은 커밋이나, 물리 트랜잭션은 롤백이기 때문에 물리적 트랜잭션의 흐름은 외부 트랜잭션을 따라가기 때문에 커밋되지 않고 롤백
    }

    private void inner_commit() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}",inner.isNewTransaction());

        log.info("내부 트랜잭션 커밋");
        txManager.commit(inner);
    }


    @Test
    void outer_commit_inner_rollback(){
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}",outer.isNewTransaction());


        inner_rollback();
        //내부 트랜잭션 롤백 => 이 시점에서 실제 실행되지는 않고 마킹만 해놓음 (트랜잭션 동기화 매니저 : rollbackOnly=true)
        // => 외부 트랜잭션은 커밋(X) only 롤백으로 진행해야 함
        //Global transaction is marked as rollback-only but transactional code requested commit

        log.info("외부 트랜잭션 커밋");

        //트랜잭션 동기화 메니저에서 rollbackOnly를 확인 후 물리트랜잭션 커밋x 롤백 싫랭함.
        // 이때 UnexpectedRollbackException 예외 던짐 (커밋은 시도했지만 예상치 못한 롤백 존재 했다는 예외)
        //(모든 트랜잭션이 다 커밋을 해야 커밋이 된다!!!!

        Assertions.assertThatThrownBy(()-> txManager.commit(outer))
                .isInstanceOf(UnexpectedRollbackException.class);

    }

    private void inner_rollback() {
        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("inner.isNewTransaction()={}",inner.isNewTransaction());

        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);  //실행되지는 않고 롤백 전용으로 표시 됨
    }


    @Test
    void inner_rollback_requires_new(){
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("outer.isNewTransaction()={}",outer.isNewTransaction());   //TRUE

        log.info("내부 트랜잭션 시작");

        innerLogic();


        //Resuming suspended transaction after completion of inner transaction : 기죈에 미뤄둔 외부 트랜잭션 다시 수행
        log.info("외부 트랜잭션 커밋");
        txManager.commit(outer);    //커밋


    }

    private void innerLogic() {
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        //DEFAULT : PROPAGATION_REQUIRED (만약 기존 트랜잭션이 존재한다면 그대로 참여하는 방법)
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //PROPAGATION_REQUIRES_NEW : 기존 트랜잭션 무시하고 항상 신규 트랜잭션을 만든다.
        //기존 커넥션은 2개가 존재 (outer 용(대기) , inner 용)

        // Suspending current transaction : current transaction(외부 트랜잭션)은 잠깐 미뤄두고 새 트랜잭션을 만듦. 커넥션 획득!
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("inner.isNewTransaction()={}",inner.isNewTransaction());   //TRUE

        log.info("내부 트랜잭션 롤백");
        txManager.rollback(inner);  //롤백
    }


}
