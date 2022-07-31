package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



@WebServlet(name="frontControllerV4",urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {
    private Map<String,ControllerV4> controllerMap = new HashMap<>();
    FrontControllerV4(){
        controllerMap.put("/front-controller/v4/members/form",new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);
        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        Map<String,String> paramMap = modelToAttribute(request);
        Map<String,Object> model = new HashMap<>(); //추가 :  model객체 컨트롤러에서 직접 제공함
        String viewName = controller.process(paramMap, model);
        MyView view = viewResolver(viewName);
        view.render(model,request,response);
    }
    public MyView viewResolver(String path){
        return new MyView("/WEB-INF/views/"+path+".jsp");
    }

    private  Map<String,String> modelToAttribute(HttpServletRequest request){
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName->paramMap.put(paramName,request.getParameter(paramName)));
        return paramMap;
    }





}
