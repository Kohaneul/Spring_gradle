package hello.thymeleafbasic.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data","Hello <b>Spring!</b>");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data","Hello <b>Spring!</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data","Spring!");
        return "basic/literal";
    }




    @GetMapping("/variable")
    public String variable(Model model){
        User userA = new User("userA", 10);
        User userB = new User("userB", 20);
        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);
        Map<String,User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);
        model.addAttribute("user",userA);
        model.addAttribute("users",list);
        model.addAttribute("userMap",map);
        return "basic/variable";
    }
    @GetMapping("/date")
    public String data(Model model){
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }



    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");
        return "basic/link";
    }

    @Data
    static class User{
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }


    @GetMapping("/basic-objects")
    public String basicObjects(HttpSession session, HttpServletRequest request){
        session.setAttribute("sessionData","Hello Session!");
        //session : 사용자가 브라우저를 종료하기 전까지 유지
        return "basic/basic-objects";
    }

    @Component("helloBean") //스프링 빈에 직접 접근
    static class HelloBean{
        public String hello(String data){
            return "Hello "+data;
        }
    }




}
