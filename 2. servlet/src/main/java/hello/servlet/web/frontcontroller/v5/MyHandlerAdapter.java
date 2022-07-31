package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;



public interface MyHandlerAdapter {
    boolean support(Object handler);    //컨트롤러가 넘어왔을때 지원할 수 있으면 true 반환

    ModelView handle(HttpServletRequest request, HttpServletResponse response,Object handler)throws ServletException, IOException;
}
