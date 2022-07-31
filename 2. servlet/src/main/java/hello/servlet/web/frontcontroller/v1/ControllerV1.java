package hello.servlet.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;





public interface ControllerV1 { //여러가지(등록,리스트보기,저장 ->다형성) 활용하기 위하여 인터페이스로 설계 
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
