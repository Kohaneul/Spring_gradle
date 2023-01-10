package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
class MemberServiceTest {
    @Autowired private MemberService memberService;
    @Autowired private LogRepository logRepository;
    @Autowired private MemberRepository memberRepository;

    /**
     *
     * memberService        @Transactional : OFF
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON
     *
     * */

    @Test
    void outerTxOff_success(){
        //given
        String userName = "outerTxOff_success";

        //when
        memberService.joinV1(userName);

        //then : 모든 데이터가 정상 저장 된다.
        assertTrue(memberRepository.findByName(userName).isPresent());
        //Optional로 반환되는 경우 데이터를 담고있기 때문에 isPresent(존재여부)를 사용함
        assertTrue(logRepository.findByMessage(userName).isPresent());
    }

    /**
     *
     * memberService        @Transactional : OFF
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON EXCEPTION
     *
     * */
    @Test
    void outerTxOff_fail(){

        //given
        String userName = "로그예외_outerTxOff_success";
        //when
        assertThatThrownBy(()->memberService.joinV1(userName)).isInstanceOf(RuntimeException.class);

        //then : log 데이터는 롤백된다.
        assertTrue(memberRepository.findByName(userName).isEmpty());
        assertTrue(logRepository.findByMessage(userName).isEmpty());
    }

    /**
     *
     * memberService        @Transactional : ON
     * logRepository        @Transactional : OFF
     *  memberRepository    @Transactional : OFF
     *
     * */
    @Test
    void singleTx(){
        //given
        String userName = "singleTx";

        //when
        memberService.joinV2(userName);

        //then : 모든 데이터가 정상 저장 된다.
        assertTrue(memberRepository.findByName(userName).isPresent());
        assertTrue(logRepository.findByMessage(userName).isPresent());
    }

//    @Test
//    void hello(){
//        org.assertj.core.api.Assertions.assertThatThrownBy(()->memberRepository.save1(new Member("hello"))).isInstanceOf(InvalidDataAccessApiUsageException.class);
//        assertTrue(memberRepository.findByName("hello").isEmpty());
//    }



    /**
     *
     * memberService        @Transactional : ON
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON
     *
     * */
    @Test
    void outerTxOn_success(){
        //given
        String userName = "outerTxOn_success";
        //when
        memberService.joinV3(userName);

       //then
        assertTrue(memberRepository.findByName(userName).isPresent());
        assertTrue(logRepository.findByMessage(userName).isPresent());

    }
    /**
     *
     * memberService        @Transactional : ON
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON Exception
     *
     * */
    @Test
    void outerTxOn_fail(){
        //given
        String userName = "로그예외_outerTxOn_fail";
        //when
        assertThatThrownBy((()-> memberService.joinV3(userName))).isInstanceOf(RuntimeException.class);

        //then : 모든 데이터가 롤백된다.
        assertTrue(memberRepository.findByName(userName).isEmpty());
        assertTrue(logRepository.findByMessage(userName).isEmpty());

    }


    /**
     *
     * memberService        @Transactional : ON
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON Exception
     *
     * */
    @Test
    void recoverException_fail(){
        //given
        String userName = "로그예외_recoverException_fail";
        //when
        assertThatThrownBy((()-> memberService.joinV4(userName))).isInstanceOf(UnexpectedRollbackException.class);

        //then : 모든 데이터가 롤백된다.
        assertTrue(memberRepository.findByName(userName).isEmpty());
        assertTrue(logRepository.findByMessage(userName).isEmpty());

    }


    /**
     *
     * memberService        @Transactional : ON
     * logRepository        @Transactional : ON
     *  memberRepository    @Transactional : ON(REQUIRES_NEW) Exception
     *
     * */
    @Test
    void recoverException_success(){
        //given
        String userName = "로그예외_recoverException_fail";
        //when
       memberService.joinV4(userName);


        //then : member 저장, log 롤백.
        assertTrue(memberRepository.findByName(userName).isPresent());
        assertTrue(logRepository.findByMessage(userName).isEmpty());

    }



}