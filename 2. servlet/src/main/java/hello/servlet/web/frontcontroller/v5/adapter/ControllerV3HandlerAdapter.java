package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



    public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
        @Override
        public boolean support(Object handler) {
            return (handler instanceof ControllerV3);
        }

        @Override
        public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
            ControllerV3 controller = (ControllerV3) handler;
            Map<String, String> paramMap = createParamMap(request);
            ModelView mv = controller.process(paramMap);
            //adapter역할 :  핸들러를 호출해주고 반환타입만 ModelView 타입으로 맞춰서 반환
            return mv;
        }

        public Map<String, String> createParamMap(HttpServletRequest request) {
            Map<String, String> model = new HashMap<>();
            request.getParameterNames().asIterator().forEachRemaining(paramName -> model.put(paramName, request.getParameter(paramName)));
            return model;
        }
    }
