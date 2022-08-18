package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //그냥 Controller일때 String타입으로 지정 가능
@Slf4j
public class ApiExceptionV2Controller {

// 해당 컨트롤러 밖에 사용할 수 없어 주석처리한 부분은 ExControllerAdvice으로 해당 코드 옮김
//    @ResponseStatus(HttpStatus.BAD_REQUEST)//해당 애노테이션을 붙이지 않으면 정상흐름이 되어서 상태코드 200이 되기 떄문에 400으로 고쳐줌
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandler(IllegalArgumentException e){
//        //해당 컨트롤러에서 IllegalArgumentException 예외가 발생하면 해당 메소드가 잡은 후 호출됨.
//        log.error("[exceptionHandler] ex",e);
//        return new ErrorResult("BAD",e.getMessage());
//    }
//
//
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> userExHandler(UserException e){
//        log.error("[exceptionHandler] ex", e);
//        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
//        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
//    }
//
//    //IllegalArgumentException, UserException 이 처리하지 못한 것들을 다 공통적으로 처리해줌
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public ErrorResult exHandler(Exception e){
//        log.error("[exceptionHandler] ex",e);
//        return new ErrorResult("EX","내부 오류");
//    }


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id")String id){
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello"+id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }

}
