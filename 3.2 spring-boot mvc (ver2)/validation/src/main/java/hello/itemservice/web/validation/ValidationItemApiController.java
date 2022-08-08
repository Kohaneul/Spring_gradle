package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult){
        //case 1) 성공
        //case 2) 실패  : json을 가지고 객체를 만들때 Controller 호출이 안된다.(HttpMessageConverter 실패)
        //case 3) 검증 오류 : JSON을 객체로 생성하는 것은 성공했으나, 검증에서 실패함
        //검증
        log.info("API 컨트롤러 호출");
        if(bindingResult.hasErrors()){
            log.info("검증 오류 발생 errors={}",bindingResult);
            return bindingResult.getAllErrors();    //list로 반환되는데 list가 json으로 바뀜.

        }
        log.info("성공 로직 실행");
        return form;
    }

}
