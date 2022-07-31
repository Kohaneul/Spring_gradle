package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j  //Lombok이 제공하는 로그 출력할 수 있는 애노테이션
@RestController     //RestController : 문자 반환->HttpMessageBody에 String이 그대로 반환함
public class LogTestController {
   // private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest(){
        String name="Spring";
 //       log.trace("trace log="+name);   //해당 로그 출력방법은 옳지 않은 방법 이유 : java가 log.trace메소드를 출력하기 전에 해당 문자열을 더하는 연산이 이루어짐
                                         //연산 사용 -> 메모리 사용, cpu 사용함 -->쓸모없는 리소스 낭비함

        log.trace("trace log={}",name); //파라미터 넘김->아무 연산이 이루어지지 않음
        log.debug("debug log={}",name); //현재 로그는 디버그할때 봄
        log.info("info log={}",name);   //현재 로그는 중요한 정보(비즈니스 정보)
        log.warn("warn log={}",name);  //현재 로그는 경고
        log.error("error log={}",name); //현재 로그는 에러

        return"ok";
    }


}
