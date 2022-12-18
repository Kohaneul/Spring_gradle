package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.ex.ErrorMessage;
import com.visit.program.ReservationProgram.domain.ex.ReviseCountExcess;
import com.visit.program.ReservationProgram.domain.service.EmployeeService;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.domain.service.VisitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation/info")
public class ReservationInfoController {
    private final ReservationService reservationService;
    private final VisitorService visitorService;
    private final EmployeeService employeeService;

    @GetMapping("/click/{id}")
    public String clickReservation(@PathVariable(name = "id") Long id, Model model) {
        Reservation reservation = reservationService.findOne(id);
        Long visitor_id = reservation.getId();
        visitorService.updateCheckedInfo(visitor_id);
        reservationService.updateCheckedInfo(id);
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "redirect:/reservation/info/all";
    }

    @GetMapping("/save")
    public String saveInfo(@ModelAttribute("visitor") SaveVisitor saveVisitor, HttpServletRequest request,Model model) {
        log.info("employee_id={}",request.getParameter("employee_Id"));
        if(request.getParameter("employee_Id")!=null){
            Long employeeId = Long.parseLong(request.getParameter("employee_Id"));
            saveVisitor.setEmployee_id(employeeId);
            Employee employee = employeeService.findOne(employeeId);
            String employeeInfo=employee.getName() + "("+employee.getDepartment()+")";
            model.addAttribute("employeeInfo",employeeInfo);
        }
        sendDate(model);
        return "/view/SaveForm";
    }

    private void sendDate(Model model) {
        model.addAttribute("month",getDate(1,12));
        model.addAttribute("day",getDate(1,31));
        model.addAttribute("hour",getDate(0,23));
        model.addAttribute("minute",getDate(0,59));
    }


    private List<Integer> getDate(int min,int max){
        List<Integer> list = new ArrayList<>();
        for(int i = min; i<=max;i++){
            list.add(i);
        }
        return list;
    }





    @PostMapping("/save")
    public String saveInfo(@Valid @ModelAttribute(name = "visitor") SaveVisitor visitor, BindingResult bindingResult) {
        notEqualEmployeeId(visitor.getEmployee_id(), bindingResult);
        if (bindingResult.hasErrors()) {
            return "/view/SaveForm";
        }
        Long visitorId = visitorService.saveInfo(visitor);
        Visitor visitor1 = visitorService.findOne(visitorId);
        reservationService.saveInfo(new SaveReservationInfo(visitor1.getEmployee_id(), visitor1.getId(), visitor1.is_checked()));
        return "redirect:/reservation/info/all";
    }



    private void notEqualEmployeeId(Long id, BindingResult bindingResult) {
        if (employeeService.findOne(id) == null) {
            bindingResult.reject("globalError", "존재하지 않는 사번입니다.");
        }
    }

    @GetMapping("/{id}")
    public String viewReservationOne(@PathVariable(name = "id") Long id, Model model) {
        Reservation reservation = reservationService.findOne(id);
        ReservationInfo reservationInfo = reservationService.findInfo(id);
        Long visitorId = reservationInfo.getVisitor_id();
        Visitor visitor = visitorService.findOne(visitorId);

        model.addAttribute("visitor",visitor);
        model.addAttribute("reservation", reservation);
        return "/view/ViewOne";
    }


    @GetMapping("/employee/find")
    public String employeeAll(Model model) {
        List<Employee> employeeList = employeeService.findAll();
        model.addAttribute("employeeList", employeeList);
        return "/view/EmployAll";
    }

    @GetMapping("/employee/{id}")
    public String employeeId(@PathVariable("id") Long id, Model model){
        Employee employee = employeeService.findOne(id);
        Long employeeId = employee.getId();
        model.addAttribute("employeeId",employeeId);
        return "/view/EmployAll";
    }


    @GetMapping("/{id}/update")
    public String updateInfo(@PathVariable Long id, Model model) {
        Visitor beforeVisitor = visitorService.findOne(id);
        UpdateVisitor updateVisitor = updateVisitor(beforeVisitor);
        model.addAttribute("visitor",updateVisitor);
        sendDate(model);
        return "/view/UpdateForm";
    }

    @PostMapping("/{id}/update")
    public String updateInfo(@PathVariable Long id, @Valid @ModelAttribute(name = "visitor") UpdateVisitor updateVisitor, BindingResult bindingResult) {
        int count = updateVisitor.getCount();
        ReviseCountEx(count);
        notEqualEmployeeId(updateVisitor.getEmployee_id(), bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(i->log.info("에러원인={}",i));
            return "/view/UpdateForm";
        }
        visitorService.updateInfo(updateVisitor);
        return "redirect:/reservation/info/{id}";
    }

    private void ReviseCountEx(int count){
        if(count>=2){
            throw new ReviseCountExcess(ErrorMessage.REVISE_COUNT_EXCESS);
        }
    }

    private UpdateVisitor updateVisitor(Visitor visitor) {
        String visitDate1 = visitor.getVisit_date1();
        String visitDate2 = visitor.getVisit_date2();
        return new UpdateVisitor(visitor.getId(), visitor.getEmployee_id(), visitor.getName(), visitor.getPurpose(),visitor.getCompany(),visitor.getPhone_number(),
                setDate(visitDate1,"월"),setDate(visitDate1,"일"),setDate(visitDate1,"시"),setDate(visitDate1,"분"),
                setDate(visitDate2,"월"),setDate(visitDate2,"일"),setDate(visitDate2,"시"),setDate(visitDate2,"분"),
                visitor.getWrite_date(), visitor.getBirth(), visitor.getCount(),visitor.is_checked());}


     private int setDate(String str,String dateType){
        StringBuilder stringBuilder =  new StringBuilder(str);
        String strNum="";
        switch(dateType){
            case "월":
                strNum = stringBuilder.substring(0, stringBuilder.indexOf("월"));
            break;
            case "일":
                strNum = stringBuilder.substring(stringBuilder.indexOf("월")+1, stringBuilder.indexOf("일"));
            break;
            case "시":
                strNum = stringBuilder.substring(stringBuilder.indexOf(" ") + 1, stringBuilder.indexOf("시"));
                break;
            case "분":
                strNum = stringBuilder.substring(stringBuilder.indexOf("시") + 1, stringBuilder.indexOf("분"));
                break;
        }
        return Integer.parseInt(strNum);
     }


    @GetMapping("/{id}/delete")
    public String deleteInfo(@PathVariable Long id) {
        Long visitorId = reservationService.findOne(id).getVisitor_id();
        reservationService.deleteInfo(id);
        visitorService.deleteInfo(visitorId);
        return "redirect:/reservation/info/all";
    }


}
