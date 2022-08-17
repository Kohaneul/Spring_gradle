package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    //원하는 웹서버를 customize 함.
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");    //400에러가 나면 에러페이지 400으로
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");//500에러가 나면 에러페이지 500으로

        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");   //Exception이 발생했을때 error-page/500이 호출
        factory.addErrorPages(errorPage404,errorPage500,errorPageEx);   //등록
    }
}
