package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.dto.ReservationDTO;
import com.visit.program.ReservationProgram.domain.ex.ErrorMessage;
import com.visit.program.ReservationProgram.domain.ex.NoModificationsEx;
import com.visit.program.ReservationProgram.domain.ex.ReviseCountExcess;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.domain.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation/info")
public class ReservationInfoController {
    private final ReservationService reservationService;
    private final VisitorService visitorService;

//
//    @GetMapping("/calender")
//    public String viewCalender() {
//        return "/view/calender";
//    }
//
//
//    @PostMapping("/calender")
//    public String viewCalender2(@RequestParam("visit_date1")String visit_date1,@RequestParam("visit_date2")String visit_date2,Model model){
//        model.addAttribute("visit_date1",visit_date1);
//        model.addAttribute("visit_date2",visit_date2);
//       log.info("visit_Date={}",visit_date1);
//        return "/view/SaveForm";
//    }
//

    @GetMapping("/save")
    public String saveInfo(@ModelAttribute("visitor")SaveVisitor visitor){
        return "/view/SaveForm";
    }

    @PostMapping("/save")
    public String saveInfo(@Valid @ModelAttribute(name = "visitor") SaveVisitor visitor, BindingResult bindingResult,Model model
    ) {
        String wrongPhoneNumber = wrongPhoneNumber(visitor.getPhone_number());
        if(wrongPhoneNumber!=null){
            log.info("wrongPhoneNumber={}",wrongPhoneNumber);
            model.addAttribute("wrongPhoneNumber",wrongPhoneNumber);
        }
        if (bindingResult.hasErrors()) {
            String errorMsg = setNullErrors(visitor,bindingResult);
            model.addAttribute("errorMsg",errorMsg);
            return "/view/SaveForm";
        }
        else {
            Long visitorId = visitorService.saveInfo(visitor);
            Visitor visitor1 = visitorService.findOne(visitorId);
            reservationService.saveInfo(new SaveReservationInfo(visitor1.getId(), visitor1.getIs_checked()));
            return "redirect:/reservation/info/all";
        }
    }


    private String setNullErrors(SaveVisitor visitor,BindingResult bindingResult) {
        int cnt = 0;
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.hasText(visitor.getEmployee_name())) {
            cnt++;
            builder.append("담당자(이름) ");
        }
        if (!StringUtils.hasText(visitor.getName())) {
            cnt++;
            builder.append("방문자(이름) ");
        }
        if (!StringUtils.hasText(visitor.getPhone_number())) {
            cnt++;
            builder.append("연락처 ");
        }
        if (!StringUtils.hasText(visitor.getCompany())) {
            cnt++;
            builder.append("소속회사 ");
        }
        if (!StringUtils.hasText(visitor.getBirth())) {
            cnt++;
            builder.append("생년월일 ");
        }
        if (!StringUtils.hasText(visitor.getPurpose())) {
            cnt++;
            builder.append("방문 목적 ");
        }
        if(cnt==0){
            return null;
        }
        else{
            String str = "다음 표시된 항목을 확인 후 다시 입력 해주세요 : "+builder.toString();
            bindingResult.reject("globalError",str);
            log.info("str={}",str);
            return str;
        }
    }


    private String wrongPhoneNumber(String phoneNumber){
        String regExp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
        if(!Pattern.matches(regExp, phoneNumber)){
            return "휴대전화 형식 오류 : 010-0000-0000 형식으로 입력해주세요";
        }
        return null;
    }





    @RequestMapping("/click/{id}")
    public void clickReservation(@ModelAttribute("reservationDTO") ReservationDTO reservationDTO, @PathVariable(name = "id") Long id,
                                   Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
        Reservation reservation = reservationService.findOne(id);
        Long visitor_id = reservation.getVisitor_id();
        Visitor visitor = visitorService.findOne(visitor_id);
        log.info("visitor is checked={}",visitor.getIs_checked());
        visitorService.updateCheckedInfo(visitor.getId());
        reservationService.updateCheckedInfo(id);
        List<Reservation> reservations = reservationService.findAllDTO(reservationDTO);
        log.info("===========================================");
        log.info("clickReservation");
        log.info("visitor_is_checked={}",visitor.getIs_checked());
        log.info("visitor_is_checked={}",reservation.getIs_checked());
        model.addAttribute("reservations", reservations);
        response.sendRedirect(redirectURL(request,response));
    }


    private String redirectURL(HttpServletRequest request, HttpServletResponse response){
        String referURL = request.getHeader("REFERER");
        response.setContentType("text/html; charset=UTF-8");
        referURL=referURL.substring(referURL.indexOf("r")-1);
        return referURL;
    }


    @GetMapping("/{id}")
    public String viewReservationOne(@PathVariable(name = "id") Long id, Model model) {
        ReservationInfo reservationInfo = reservationService.findInfo(id);
        Long visitorId = reservationInfo.getVisitor_id();
        Visitor visitor = visitorService.findOne(visitorId);
        boolean checked = visitor.getIs_checked();
        log.info("controller.viewReservationOne");
        log.info("=================================");
        log.info("visitor={}",checked);
        log.info("reservation={}",reservationInfo.getIs_checked());

        model.addAttribute("visitor",visitor);
        model.addAttribute("reservation", reservationInfo);
        return "/view/ViewOne";
    }

    @GetMapping("/{id}/update")
    public String updateInfo(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.findOne(id);
        Visitor beforeVisitor = visitorService.findOne(reservation.getVisitor_id());
        log.info("controller.updateInfo");

        log.info("visitor={}",beforeVisitor.getIs_checked());
        log.info("reservation={}",reservation.getIs_checked());
        UpdateVisitor updateVisitor = updateVisitor(beforeVisitor);
        model.addAttribute("visitor",updateVisitor);
        log.info("update Visitor ={}",updateVisitor.getIs_checked());
        return "/view/UpdateForm2";
    }

    @PostMapping("/{id}/update")
    public String updateInfo(@PathVariable Long id, @Valid @ModelAttribute(name = "visitor") UpdateVisitor updateVisitor, BindingResult bindingResult) throws IOException {
        int count = updateVisitor.getCount();
        log.info("update Visitor ={}",updateVisitor.getIs_checked());

        log.info("controller.updateInfo POST");
        ReviseCountEx(count);

        NoModificationEx(id,updateVisitor);


        if (bindingResult.hasErrors()) {
            return "/view/UpdateForm2";
        }
        visitorService.updateInfo(updateVisitor);
        log.info("visitor={}",updateVisitor.getIs_checked());
        log.info("update count after = {}",updateVisitor.getCount());

        return "redirect:/reservation/info/{id}";
    }

    private void NoModificationEx(Long id,UpdateVisitor updateVisitor) {
        Reservation reservation = reservationService.findOne(id);
        Visitor visitor = visitorService.findOne(reservation.getVisitor_id());

        if (visitor.getEmployee_name().equals(updateVisitor.getEmployee_name()) &&
                visitor.getPhone_number().equals(updateVisitor.getPhone_number()) &&
                    visitor.getName().equals(updateVisitor.getName()) &&
                    visitor.getCompany().equals(updateVisitor.getCompany()) &&
                    visitor.getBirth().equals(updateVisitor.getBirth()) &&
                    visitor.getPurpose().equals(updateVisitor.getPurpose()) &&
                    visitor.getVisit_date1().equals(updateVisitor.getVisit_date1()) &&
                    visitor.getVisit_date2().equals(updateVisitor.getVisit_date2())) {
            throw new NoModificationsEx(ErrorMessage.NO_REVISE_MSG);
        }
    }

    private void ReviseCountEx(int count){
        if(count>=2){
            throw new ReviseCountExcess(ErrorMessage.REVISE_COUNT_EXCESS);
        }
    }

    private UpdateVisitor updateVisitor(Visitor visitor) {
        log.info("UpdateVisitor.updateVisitor");
        log.info("visitor is checked={}",visitor.getIs_checked());
        return new UpdateVisitor(visitor.getEmployee_name(),visitor.getName(),visitor.getPhone_number(),visitor.getCompany(),visitor.getVisit_date1(),visitor.getVisit_date2()
                ,visitor.getBirth(),visitor.getPurpose(),visitor.getWrite_date(),visitor.getCount(),visitor.getIs_checked(),visitor.getId());}


    @GetMapping("/{id}/delete")
    public String deleteInfo(@PathVariable Long id){
        Long visitorId = reservationService.findOne(id).getVisitor_id();
        reservationService.deleteInfo(id);
        visitorService.deleteInfo(visitorId);
        return "redirect:/reservation/info/all";
    }


}