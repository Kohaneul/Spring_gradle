package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
public class ServletExController {
    @GetMapping("/error-ex")
    public void errorEx(){
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400,"400 오류");   //상태코드 지정
        //response 내부에 오류가 발생했다는 상태 저장.
        //응답 전에 response에 sendError가 호출되었는지 확인한 후 기본 오류페이지를 보여줌
    }


    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404,"404 오류");   //상태코드 지정
        //response 내부에 오류가 발생했다는 상태 저장.
        //응답 전에 response에 sendError가 호출되었는지 확인한 후 기본 오류페이지를 보여줌
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }

}
