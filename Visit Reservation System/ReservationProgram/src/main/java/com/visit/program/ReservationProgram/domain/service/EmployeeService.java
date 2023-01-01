//package com.visit.program.ReservationProgram.domain.service;
//
//import com.visit.program.ReservationProgram.domain.dao.*;
//import com.visit.program.ReservationProgram.domain.dto.EmployeeSearchDTO;
//import com.visit.program.ReservationProgram.domain.repository.EmployeeRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class EmployeeService {
//    @Autowired private final EmployeeRepository employeeRepository;
//
//    public List<Employee> findAll(){
//        return employeeRepository.findAll();
//    }
//    public List<Employee> findAllDTO(EmployeeSearchDTO employeeSearchDTO){
//        return employeeRepository.findAllDTO(employeeSearchDTO);
//    }
//
//    public Employee findOne(Long id) {
//        return employeeRepository.findOne(id);
//    }
//
//
//    @Transactional
//    public void saveInfo(SaveEmployee saveEmployee, String date) {
//        employeeRepository.saveInfo(saveEmployee, date);
//    }
//
//    @Transactional
//    public void deleteInfo(Long id) {
//        employeeRepository.deleteInfo(id);
//    }
//
//
//    @Transactional
//    public void updateInfo(UpdateEmployee updateEmployee) {
//        employeeRepository.updateInfo(updateEmployee.getName(), updateEmployee.getDepartment(), updateEmployee.getEmail(), updateEmployee.getGender(), updateEmployee.getId());
//    }
//}
