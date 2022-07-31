package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name="requestBodyStringServlet",urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        String s = StreamUtils.copyToString(inputStream,StandardCharsets.UTF_8);
        resp.getWriter().write("내용 : "+s);
    }
}


//
//@WebServlet(name="requestBodyStringServlet", urlPatterns = "/request-body-string")
//public class RequestBodyStringServlet extends HttpServlet {
//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ServletInputStream inputStream = request.getInputStream();
//        //메세지 바디의 내용을 byte 코드로 바꿀 수 있음
//        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//        response.getWriter().write("ok");
//
//    }
//
//
//
//}
