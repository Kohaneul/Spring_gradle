package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="mvcMemberFormServlet",urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String viewPath = "/WEB-INF/views/new-form.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);//컨트롤러->뷰로 이동
            dispatcher.forward(request,response);//jsp 로 넘어가서 호출

        //redirect : 서버 응답 후 Location Header url이 있으면 자동 이동했다가 다시 서버호출(호출 2번)
        //forward : 웹브라우저 입장에서는 호출 후 서버 내부에서 자기들끼리 주고받은 뒤 바로 받음. 클라이언트가 인지X


    }
}
