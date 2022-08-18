package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    //ExceptionResolver : Exception을 해결해주어 정상적인 흐름으로 돌아가게끔 해주는 것
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("call resolver={}",ex);
        //Exception이 넘어오면 정상적인 ModelAndView로 넘겨줌
        try {
            if(ex instanceof IllegalArgumentException) { //IllegalArgumentException이 터지면
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());//400오류로 한다
                return new ModelAndView();  //빈 ModelAndView 로 반환한다. , DispatcherServlet 으로 넘어감
            }

            }
        catch (IOException e) {
            log.error("resolver ex",e);
                ex.printStackTrace();
            }

        return null;    //기존에 발생한 예외를 서블릿 컨테이너로 던진다.
    }


}
