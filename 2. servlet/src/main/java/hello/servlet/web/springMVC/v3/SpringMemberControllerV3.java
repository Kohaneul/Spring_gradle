package hello.servlet.web.springMVC.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    private final MemberRepository memberRepository = MemberRepository.getInstance();

    //view 반환 방법 ==> ModelAndView, String타입 반환

    @GetMapping("/new-form")
    public String form(){
        return "new-form";
    }

    @PostMapping("/save")
    public String save(Model model, @RequestParam("username")String username,@RequestParam("age")int age){
        Member member = new Member(username,age);
        memberRepository.save(member);
        model.addAttribute("member",member);
        return "save-result";
    }

    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "members";
    }
}
