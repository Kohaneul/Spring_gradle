package hello.servlet.web.frontcontroller.v3;

import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    FrontControllerServletV3(){
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        Map<String,String> paramMap = setMap(request);//request로부터 받은 변수를 map에 담아줌
        ModelView mv = controller.process(paramMap);
        Map<String, Object> model = mv.getModel();

    }

    public Map<String,String> setMap(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(paramName->map.put(paramName,request.getParameter(paramName)));
        return map;
    }




    //
//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String requestURI = request.getRequestURI();
//        System.out.println("***************************");
//        System.out.println("requestURI = " + requestURI);   // /front-controller/v3/members/new-form
//        //매핑정보 가져오고
//
//        ControllerV3 controllerV3 = controllerMap.get(requestURI);
//        if(controllerV3 ==null){
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//        }
//
//        //paramMap
//        Map<String, String> paramMap = createParamMap(request);
//        //모든 파라미터 이름을 다 가져와서 iterator로 돌려서 paramName에 다 집어넣음
//        ModelView mv = controllerV3.process(paramMap);
//        //논리이름 : new-form 이름
//        String viewName = mv.getViewName();//논리이름
//        MyView myView = viewResolver(viewName);
//        myView.render(mv.getModel(),request,response);  //모델정보 넘겨주면 다꺼내서 setattribute에 다 넣음
//    }
//
//    private MyView viewResolver(String viewName) {  //논리이름을 물리이름으로 반환
//        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
//    }
//
//    private Map<String, String> createParamMap(HttpServletRequest request) {
//        Map<String,String> paramMap = new HashMap();          //Map을 만들어서
//        request.getParameterNames().asIterator().forEachRemaining(paramName->paramMap.put(paramName,request.getParameter(paramName))
//        );
//
//        return paramMap;
//    }
}
