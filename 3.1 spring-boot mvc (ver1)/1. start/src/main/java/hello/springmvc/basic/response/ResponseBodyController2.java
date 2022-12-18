package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class ResponseBodyController2 {

    @GetMapping("/response-body-string-v12")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v22")
    public ResponseEntity<String> responseBodyV2(HttpServletResponse response) throws IOException {
       return new ResponseEntity<>("ok",HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v33")
    public String responseBodyV33() throws IOException {
        return "ok";
    }

    @GetMapping("/response-body-json-v1111")
    public ResponseEntity<HelloData> responseBodyJson1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData,HttpStatus.OK);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    @GetMapping("/response-body-json-v1122")
    public HelloData responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }


}
