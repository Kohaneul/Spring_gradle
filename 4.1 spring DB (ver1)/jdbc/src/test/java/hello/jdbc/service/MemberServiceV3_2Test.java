package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 * */
@Slf4j
class MemberServiceV3_2Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_Ex = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_2 service;

    @BeforeEach
    void before() throws SQLException {
       DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
       memberRepository = new MemberRepositoryV3(dataSource);
       PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
       //DataSourceTransactionManager : JDBC용 트랜잭션 매니저
        service = new MemberServiceV3_2(transactionManager,memberRepository);
    }
    @AfterEach
    void after() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_Ex);

    }
    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A,10000);
        Member memberB = new Member(MEMBER_B,10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        log.info("START TX");
        service.accountTransfer(memberA.getMemberId(),memberB.getMemberId(),2000);
        log.info("END TX");


        //then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB= memberRepository.findById(memberB.getMemberId());

        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }


    @Test
    @DisplayName("이체 중 예외터짐")
    void accountTransferEx() throws SQLException {
        //given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_Ex, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);
        //when

        assertThatThrownBy(()-> service.accountTransfer(memberA.getMemberId(),memberEx.getMemberId(),2000)).isInstanceOf(IllegalStateException.class);
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberEx = memberRepository.findById(memberEx.getMemberId());

        //then
        //exception이 터지면 실행이 안되고 rollback이 되기 때문에 돈의 값은 변하지 않는다.
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}