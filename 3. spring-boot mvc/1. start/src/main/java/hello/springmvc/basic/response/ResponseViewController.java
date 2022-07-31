package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data","hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data","hello!");
        return "/response/hello";
    }

    @RequestMapping("/response/hello")  //컨트롤러의 이름 == view의 논리적이름이 같으면 논리적 view의 이름으로 진행됨. 하지만 권장하지않음
    public void responseViewV3(Model model){
        model.addAttribute("data","hello!");

    }
}
