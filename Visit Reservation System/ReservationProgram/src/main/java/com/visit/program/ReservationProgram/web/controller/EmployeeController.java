//package com.visit.program.ReservationProgram.web.controller;
//
//import com.visit.program.ReservationProgram.domain.dao.Employee;
//import com.visit.program.ReservationProgram.domain.dto.EmployeeSearchDTO;
//import com.visit.program.ReservationProgram.domain.ex.ErrorMessage;
//import com.visit.program.ReservationProgram.domain.ex.NothingEmployeeEx;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/reservation/info")
//public class EmployeeController {
//    private final EmployeeService employeeService;
//
//    @GetMapping("/employee")
//    public String employeeViewAll(@ModelAttribute("employeeSearchDTO")EmployeeSearchDTO employeeSearchDTO,Model model) {
//        List<Employee> employeeList = employeeService.findAllDTO(employeeSearchDTO);
//        if(findByName(employeeList.size())){
//            model.addAttribute("employeeList",employeeList);
//            return "/view/EmployAll";
//        }
//        else{
//            throw new NothingEmployeeEx(ErrorMessage.NOTHING_EMPLOYEE_EX);
//        }
//    }
//    private boolean findByName(int no){
//        return no>0?true:false;
//    }
//
//
//
//
//
//
//}
