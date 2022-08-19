package hello.typeconverter.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data");//문자타입으로 조회
        Integer intValue = Integer.parseInt(data);
        log.info("intValue={}",intValue);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data){
        //RequestParam : 문자타입 -> 숫자타입으로 스프링이 내부에서 변환을 해준다.
        log.info("data={}",data);
        return "ok";
    }


}
