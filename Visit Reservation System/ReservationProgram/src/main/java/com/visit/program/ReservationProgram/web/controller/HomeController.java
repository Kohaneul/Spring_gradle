package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.Criteria;
import com.visit.program.ReservationProgram.domain.dao.PageMaker;
import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dto.PagerDTO;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ReservationService reservationService;

    @GetMapping("/")
    public String viewAll(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        reservationService.findAll();
        model.addAttribute("reservations",reservations);
        return "/view/All";
    }


    @GetMapping("/reservation/info/all")
    public String viewAll3(@ModelAttribute("pager") PagerDTO page, Model model) {
        int startBoardNo = 0;
        int endBoardNo = 0;
        try{
            startBoardNo = page.getPage();
        }
        catch(NullPointerException e){
            startBoardNo = 1;
            endBoardNo = startBoardNo+7;
        }
        finally{
            log.info("startPAge={}",startBoardNo);
            Criteria cri = new Criteria(startBoardNo);
            PageMaker pageMaker = new PageMaker();
            pageMaker.setCri(cri);
            pageMaker.setTotalCount(100);
            List<Reservation> reservations = reservationService.findAllPages(startBoardNo,endBoardNo);
            model.addAttribute("reservations", reservations);
            model.addAttribute("pageMaker", pageMaker);
        }

        return "/view/All";
    }



}
