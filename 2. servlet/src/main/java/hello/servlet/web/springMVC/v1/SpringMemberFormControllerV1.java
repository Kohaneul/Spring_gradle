package hello.servlet.web.springMVC.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@Component
//@RequestMapping //Component 덕에 RequestMapping이 스프링 Bean으로 등록 되었다.(단, class 레벨에 넣어줘야함)
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form");
    }

}
