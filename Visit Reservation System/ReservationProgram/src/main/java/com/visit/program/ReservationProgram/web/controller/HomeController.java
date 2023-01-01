package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import com.visit.program.ReservationProgram.domain.dto.ReservationDTO;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.domain.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ReservationService reservationService;

    private final VisitorService visitorService;

    @GetMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/reservation/info/all/rapigen_security")
    public String viewSecurity() {
        return "redirect:/reservation/info/all";
    }

    @RequestMapping("/reservation")
    public String redirectReservation(RedirectAttributes redirectAttributes){
        String renewDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss"));
        redirectAttributes.addAttribute("renewDate",renewDate);
        log.info("renewDate={}",renewDate);
        return "redirect:/reservation/info/all";
    }

    @GetMapping("/reservation/info/all/rapigen_employee")
    public String viewEmployees(HttpSession session) {
        session.setAttribute("employeeSession","employees");
        return "redirect:/reservation/info/all";
    }

    @GetMapping("/reservation/info/all")
    public String viewAll(Model model, @ModelAttribute("reservationDTO")ReservationDTO reservationDTO) {

        List<Reservation> reservations = reservationService.findAllDTO(reservationDTO);
        model.addAttribute("reservations",reservations);
        String renewDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss"));
        model.addAttribute("renewDate",renewDate);
        return "/view/All";
    }

    @GetMapping("/reservation/info/all_")
    public String viewAll2(HttpSession session,@ModelAttribute("reservationDTO")ReservationDTO reservationDTO){
        if(session.getAttribute("employeeSession")!=null){
            return "redirect:/reservation/info/all/rapigen_employee";
        }
        else{
            return "redirect:/reservation/info/all/rapigen_security";
        }
    }










}
