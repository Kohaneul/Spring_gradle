package hello.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;
    @Transactional
    public void joinV1(String userName){
        Member member = new Member(userName);
        Log logMessage = new Log(userName);
        log.info("====memberRepository 호출 시작!");

        memberRepository.save(member);

        log.info("====logRepository 호출 시작!");

        logRepository.save(logMessage);


        log.info("저장 완료!");

    }

    public void joinV2(String userName){
        Member member = new Member(userName);
        Log logMessage = new Log(userName);
        log.info("====memberRepository 호출 시작!");

        memberRepository.save1(member);

        log.info("====logRepository 호출 시작!");

        logRepository.save1(logMessage);

        log.info("저장 완료!");

    }


    @Transactional
    public void joinV3(String userName){
        Member member = new Member(userName);
        Log logMessage = new Log(userName);
        log.info("====memberRepository 호출 시작!");

        memberRepository.save(member);

        log.info("====logRepository 호출 시작!");

        logRepository.save(logMessage);


        log.info("저장 완료!");

    }


    @Transactional
    public void joinV4(String userName){
        Member member = new Member(userName);
        Log logMessage = new Log(userName);
        log.info("====memberRepository 호출 시작!");

        memberRepository.save(member);

        log.info("====logRepository 호출 시작!");
        try {
            logRepository.save(logMessage);
        }
        catch(RuntimeException e){  //예외 잡아서 복구
            log.info("log 저장 실패! logMessage={}",logMessage.getMessage());
            log.info("정상흐름 반환");
        }
        log.info("====logRepository 호출 종료!");

        log.info("저장 완료!");

    }




}
