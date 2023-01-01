package com.visit.program.ReservationProgram.web.controller;
import com.visit.program.ReservationProgram.domain.ex.NothingEmployeeEx;
import com.visit.program.ReservationProgram.domain.ex.ReviseCountExcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;

@Slf4j
@ControllerAdvice
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

  @ExceptionHandler({BindException.class,NothingEmployeeEx.class})
  public void NumberFormatEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
      Error error = new Error();
      String message = error.getMessage();
      String referURL = request.getHeader("REFERER");
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      referURL=referURL.substring(referURL.indexOf("r")-1);

      out.println("<script>alert('"+message+"'); location.href='"+referURL+"';</script>");
      out.flush();
  }

  @ExceptionHandler(ReviseCountExcess.class)
  public void ReviseCountExcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
    log.info("error!!!!!!!");
    String referURL = request.getHeader("REFERER");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    referURL=referURL.substring(referURL.indexOf("r")-1);
    out.println("<script>alert('수정 가능 횟수를 초과하였습니다.(2번 이상 불가)'); location.href='"+referURL+"';</script>");
    out.flush();
  }



}
