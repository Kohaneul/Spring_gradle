package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServlet;
import java.io.IOException;


@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api") //해당 패키지에만 적용
public class ExControllerAdvice {
    //@RestControllerAdvice(annotations = RestController.class)
    //RestController => ControllerAdvice+ResponseBody
    //RestController에 예외가 터졌을때 처리
    //여러 컨트롤러에서 발생한 Exception 들을 모아서 처리

    @ResponseStatus(HttpStatus.BAD_REQUEST)//해당 애노테이션을 붙이지 않으면 정상흐름이 되어서 상태코드 200이 되기 떄문에 400으로 고쳐줌
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        //해당 컨트롤러에서 IllegalArgumentException 예외가 발생하면 해당 메소드가 잡은 후 호출됨.
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult("BAD",e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    //IllegalArgumentException, UserException 이 처리하지 못한 것들을 다 공통적으로 처리해줌
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult("EX","내부 오류");
    }

}
