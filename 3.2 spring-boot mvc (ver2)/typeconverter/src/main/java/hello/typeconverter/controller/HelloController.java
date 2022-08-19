package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
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

    @GetMapping("/hello-v2")    //"10,000" => 10000로 변환
    public String helloV2(@RequestParam Integer data){
        //RequestParam : 문자타입 -> 숫자타입으로 스프링이 내부에서 변환을 해준다.
        System.out.println("data = " + data);
        return "ok";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort){
        System.out.println("ipPort.getIp() = " + ipPort.getIp());
        System.out.println("ipPort.getPort() = " + ipPort.getPort());
        return "ok";
    }

}
