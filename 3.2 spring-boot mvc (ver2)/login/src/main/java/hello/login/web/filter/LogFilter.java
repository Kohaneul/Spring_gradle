package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");
        HttpServletRequest httpRequest=(HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        try{
            log.info("REQUEST [{}][{}]",uuid,requestURI);
           chain.doFilter(request,response);        //doFilter이 없다면 웹페이지실행이 안됨. try, finally 구문에서만 로그 찍고 끝남
           //다음필터가 있다면 다음필터로 호출. 없으면 서블릿 호출->컨트롤러 호출

        }
        catch(Exception e){
            throw e;
        }
        finally{
            //마지막 호출
            log.info("RESPONSE [{}][{}]",uuid,requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");

    }
}
