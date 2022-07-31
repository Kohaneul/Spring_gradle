package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {
//    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/hello-basic","/hello-go"},method = RequestMethod.GET)
    public String helloBasic() {
        log.info("basic");
        return "ok";
    }


    @RequestMapping(value="/mapping-get-v1",method=RequestMethod.GET)
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }

    @GetMapping("/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "ok";
    }
    /**
     * PathVariable(경로변수) 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     * /mapping/userA
     * */
    @GetMapping("/mapping/users/{userId}")
    public String  mappingPath(@PathVariable String userId){
        //@PathVariable String userId== @PathVariable("userId)String data 로도 사용 가능
        log.info("mappingPath userId={}",userId);
        return "ok";
    }
    /**
     * PathVariable 사용 다중
     * */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String  mappingPath(@PathVariable String userId,@PathVariable Long orderId){
        log.info("mappingPath userId={} orderId={}",userId,orderId);
        return "ok";
    }


    @GetMapping(value="/mapping-param",params="mode=debug")
    public String mappingParam(){
        //파라미터에 mode=debug가 호출이 되어야(/mapping-param?mode=debug) 해당 컨트롤러가 호출됨
        log.info("mappingParam");
        return "ok";
    }


    @GetMapping(value="/mapping-header",headers="mode=debug")
    public String mappingHeader(){
        //헤더에 mode=debug가 호출이 되어야(/mapping-param?mode=debug) 해당 컨트롤러가 호출됨
        log.info("mappingHeader");
        return "ok";
    }



    /**
     * Content-type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     * */
    @PostMapping(value="/mapping-consume",consumes = MediaType.APPLICATION_JSON_VALUE)
    //Header의 ContentType이 application/json일 경우에 호출
    //consume : 요청 헤더의 content-type
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces= "text/html"
     * produces="!text/html"
     * produces="text/*"
     * produces="*\/*"
     * */
    @PostMapping(value="/mapping-produce",consumes = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces(){
        //accpet가 text/html 이어야 한다. client가 text/html이 ContentType일때 받아드릴 수 있다는 의미
        //produce:요청헤더의 accept
        log.info("mappingProduces");
        return "ok";
    }



}
