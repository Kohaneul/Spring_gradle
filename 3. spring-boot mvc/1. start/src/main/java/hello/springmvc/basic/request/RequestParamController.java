package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}",username);
        log.info("age={}",age);
        response.getWriter().write("ok");
    }

    @ResponseBody   //@RestController과 비슷한 역할을 함
    @RequestMapping("/request-param-v2")
    public String requestParam2(@RequestParam("username")String username, @RequestParam("age")int age){
        log.info("username={}, age={}",username,age);

        return "ok";
    }


    @ResponseBody   //@RestController과 비슷한 역할을 함
    @RequestMapping("/request-param-v3")
    public String requestParam3(@RequestParam String username, @RequestParam int age){
        log.info("username={}, age={}",username,age);

        return "ok";
    }


    @ResponseBody   //@RestController과 비슷한 역할을 함
    @RequestMapping("/request-param-v4")
    public String requestParam4(String username, int age){  //요청파라미터 이름과 동일해야함
        log.info("username={}, age={}",username,age);
        return "ok";
    }




    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true)String username,@RequestParam(required = false)Integer age){
        //required = true : http url username이 꼭 들어와야함
        //          false : age는 없어도 된다.
        // 하지만 age를 파라미터로 넣어주지 않으면 500에러가 생김 이유 : age는 int 타입인데 파라미터를 넣어주지 않으면 null이 된다
        //int는 null이 들어갈 수 없어서 Integer(int의 객체타입)타입으로 해줘야한다
        log.info("username={},age={}",username,age);
        //http://localhost:9090/request-param-required?username=
        //로 작성해주게 되면 username에 null이 아닌, ""(빈문자)가 들어오게 된다.

        return "ok";
    }





    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true,defaultValue="guest")String username,@RequestParam(required = false,defaultValue="-1")int age){
        //default value : 없으면(빈문자인 경우에도) 해당 설정 값으로 대체하겠다

        log.info("username={},age={}",username,age);
        //로그에 찍힌 값 : username=guest,age=-1

        return "ok";
    }
//



    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String,Object> paramMap){
        //모든 파라미터를 받고싶으면 map으로 받으면 된다.

        log.info("username={},age={}",paramMap.get("username"),paramMap.get("age"));


        //?userIds=id1&userIds=id2 ==> userIds가 여러개 들어오면 MultiValueMap으로 사용하면 된다.
        return  "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
            //ModelAttribute : 객체 생성, 파라미터 값 모두 넣어줌(setxxx를 찾음)
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());

        return"ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2( HelloData helloData){
        //ModelAttribute 생략가능
        log.info("username={},age={}",helloData.getUsername(),helloData.getAge());
        return"ok";
    }

}
