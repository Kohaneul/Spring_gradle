package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.sql.SQLException;

/**
 * 트랜잭션 - @Transactional AOP
 * */
@Slf4j
public class MemberServiceV3_3 {

    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_3(MemberRepositoryV3 memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional  //이 메서드 호출시 Transaction 걸고 시작하겠다는 의미 성공하면 commit, 예외가 터지면 rollback
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        bizLogic(fromId, toId, money);
    }
    //순수한 비즈니스 로직만 남기고 애노테이션 추가, 트랜잭션 코드관련 모두 제거


    private void bizLogic(String fromId, String toId, int money) throws SQLException {
            Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromMember.getMemberId(), fromMember.getMoney()- money);
        validation(toMember);
        memberRepository.update(toMember.getMemberId(),toMember.getMoney()+ money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("아이디 오류");
        }
    }


}