package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;
   // @GetMapping("/")
    public String home() {
        return "home";
    }

  //  @GetMapping("/")    //required=false : 로그인 안한 사용자도 들어와야하기 때문
//    public String loginHomeV1(@CookieValue(name="memberId",required = false)Long memberId, Model model){
//        //memberId를 String 으로 보냈으나(LoginController login 메서드 참고), 스프링이 알아서 Long 타입으로 컨버팅 해줌.
//         if(memberId==null){
//             return "home";
//         }
//         //로그인 성공
//        Member loginMember = memberRepository.findById(memberId);   //DB에서 찾은 member가 없으면
//             if(loginMember==null){
//                 return "home"; //home으로 보낸다.
//             }
//             model.addAttribute("member",loginMember);
//
//        return "/members/loginHome";
//    }


  //  @GetMapping("/")
    public String loginHomeV2(HttpServletRequest request,Model model){
        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member)sessionManager.getSession(request);
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);
        return "/members/loginHome";
    }

  //  @GetMapping("/")
    public String loginHomeV3(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);    //세션을 생성하지 않으므로 false
        //세션이 유지되면 로그인으로 이동
        //세션에 회원데이터가 없으면 home
        if(session==null){
            return "home";
        }
        Member member=(Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(member==null){
            return "home";
        }
        model.addAttribute("member",member);

        return "/members/loginHome";

    }

   // @GetMapping("/")
    public String loginHomeV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER,required = false)Member loginMember, Model model){
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "/members/loginHome";
    }

    @GetMapping("/")
    public String homeLoginV4ArgumentResolver(@Login Member loginMember, Model model){
        if(loginMember==null){
            return "home";
        }
        model.addAttribute("member",loginMember);
        return "/members/loginHome";
    }

//    @GetMapping("/")
//    public String homeLoginV4ArgumentResolver(@Login Member loginMember, Model model){
//        if(loginMember==null){
//            return "home";
//        }
//        model.addAttribute("member",loginMember);
//        return "/members/loginHome";
//    }

}