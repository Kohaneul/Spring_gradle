package hello.servlet.web.springMVC.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private MemberRepository memberRepository= MemberRepository.getInstance();
    @RequestMapping("/new-form")
    public ModelAndView modelAndView(){
        ModelAndView mv = new ModelAndView("new-form");
        return mv;
    }
    //springmvc/v2/members
    @RequestMapping
    public ModelAndView modelAndView2(){
        ModelAndView mv = new ModelAndView("members");
        List<Member> members = memberRepository.findAll();
        mv.addObject("members",members);
        return mv;
    }


    @RequestMapping("/save")
    public ModelAndView modelAndView3(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView("save-result");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(username,age);
        memberRepository.save(member);
        mv.addObject("member",member);
        return mv;
    }

}
