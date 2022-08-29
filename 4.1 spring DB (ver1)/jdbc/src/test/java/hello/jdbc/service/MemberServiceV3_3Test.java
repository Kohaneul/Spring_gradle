package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - @Transactional AOP
 * */
@Slf4j
@SpringBootTest //Test시 스프링 컨테이너를 띄움
class MemberServiceV3_3Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_Ex = "ex";

    @Autowired private MemberRepositoryV3 memberRepository;
    @Autowired private MemberServiceV3_3 service;


    @TestConfiguration
    static class TestConfig{
        @Bean
        DataSource dataSource(){
            return new DriverManagerDataSource(URL,USERNAME,PASSWORD);
        }

        @Bean
        PlatformTransactionManager transactionManager(){
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3(){
            return new MemberRepositoryV3(dataSource());
        }
        @Bean
        MemberServiceV3_3 memberServiceV3_3(){
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }

    @Test
    void AopCheck(){
        log.info("memberService class={}",service.getClass());  //AOP프록시 적용(@Transactional 애노테이션 有) =>CGLIB 라이브러리를 통해 바이트코드조작함
        log.info("memberRepository class={}",memberRepository.getClass());
        //AopProxy 적용여부 판단
        Assertions.assertThat(AopUtils.isAopProxy(service)).isTrue();
        Assertions.assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();

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
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        //오류=>트랜잭션 처리 안된채 2000원 이체만 됐음.. 이유 : 스프링 컨테이너를 사용안했기 때문(스프링 AOP를 쓰려면 스프링 컨테이너에 스프링 빈을 등록해야함)
        assertThat(findMemberEx.getMoney()).isEqualTo(10000);
    }

}