package haneul.haneulspring.web;

import haneul.haneulspring.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger; //가짜 myLogger을 집어넣고

    @RequestMapping("log-demo")
    @ResponseBody   //화면 대신 문자로 반환
    public String logDemo(HttpServletRequest request) throws InterruptedException {  //HttpServletRequest을 통해 요청 URL을 받음
        String requestURL = request.getRequestURL().toString();
        System.out.println("myLogger = "+ myLogger.getClass()); //CGLIB로 내 클래스를 상속받은 가짜 프록시 객체 생성 후 주입
        myLogger.setRequestURL(requestURL); //requestURL을 LOGGER에 넣는다.requestURL 값 : localhost:8080/log-demo
        //provider가 동작햇던것처럼 동작함함
        //위 2줄 코드는 인터셉터로 구현해야함.
        myLogger.log("controller test");    //컨트롤러에서는 서비스 호출
        Thread.sleep(1000);

        logDemoService.logic("testId");

       return "OK";
    }
}
