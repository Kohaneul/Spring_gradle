package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
public class LoginController {
/**
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute("login")Login login){
        return "/view/Login";
    }
    @PostMapping("/login")
    public String login2(@Valid @ModelAttribute("login")Login login, HttpSession session, RedirectAttributes redirectAttributes){
        Employee employee = loginService.loginMember(login);
        log.info("loginId={}",login.getLoginId());
        log.info("pw={}",login.getPassword());
        if(employee!=null){
            Long id = employee.getId();
            log.info("id={}",id);
            session.setAttribute(SessionConst.loginId,id);
            redirectAttributes.addAttribute("id",id);
            return "redirect:/reservation/info/all";
        }
        else{
            log.info("로그인 실패");
            return "/view/Login";
        }
    }



    @GetMapping("/reservation/logOut")
    public String logOut(HttpSession session){
        session.invalidate();
        return "redirect:/reservation/login";
    }

 */


}
