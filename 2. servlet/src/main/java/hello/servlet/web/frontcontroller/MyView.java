package hello.servlet.web.frontcontroller;

import lombok.Data;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class MyView {

    private String viewPath;    //"save"

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }


    //실제 view가 렌더링 되도록 동작
    public void render(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }




    public void render(Map<String,Object> model,HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //model의 값을 꺼내서 httpservletRequest에 다 넣음
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request,response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        //모델에 있는 객체를 다 꺼내서 request 값을 다 담음
        model.forEach((key, value)-> request.setAttribute(key,value));
    }
}
