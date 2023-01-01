package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController2 {
    private ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/request-body-json-v2222")
    public void requestBodyJsonV2(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());
        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v333")
    public String requestBodyJsonV333(@RequestBody String messageBody) throws IOException {
       log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }



    @ResponseBody
    @PostMapping("/request-body-json-v332223")
    public String requestBodyJsonV323(@RequestBody HelloData helloData) {
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v32342342")
    public String requestBodyJsonV22323(HttpEntity<HelloData> helloData){
        HelloData helloData1 = helloData.getBody();
        log.info("username={},age={}",helloData1.getUsername(),helloData1.getAge());
        return "ok";
    }


    @ResponseBody
    @PostMapping("/request-body-json-v232422")
    public HelloData requestFawefaw(@RequestBody HelloData helloData){
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());
        return helloData;
    }
}
