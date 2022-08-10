package hello.login.web;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

   // @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")    //required=false : 로그인 안한 사용자도 들어와야하기 때문
    public String loginHome(@CookieValue(name="memberId",required = false)Long memberId, Model model){
        //memberId를 String 으로 보냈으나(LoginController login 메서드 참고), 스프링이 알아서 Long 타입으로 컨버팅 해줌.
         if(memberId==null){
             return "home";
         }
         //로그인 성공
        Member loginMember = memberRepository.findById(memberId);   //DB에서 찾은 member가 없으면
             if(loginMember==null){
                 return "home"; //home으로 보낸다.
             }
             model.addAttribute("member",loginMember);

        return "/members/loginHome";
    }
}