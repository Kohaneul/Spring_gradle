package hello.servlet.web.springMVC.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SpringMemberListControllerV1 {
    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView modelAndView(){
        ModelAndView mv = new ModelAndView("members");
        List<Member> members = memberRepository.findAll();
        mv.addObject("members",members);
        return mv;
    }

}
