package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final EmployeeRepository employeeRepository;
    public Employee loginMember(Login login){
        String loginId = login.getLoginId();
        String password = login.getPassword();
        log.info("loginId={}, password={}",loginId,password);
        return employeeRepository.findAll().stream().filter(i->i.getLoginId().equals(loginId)&& i.getPassword().equals(password)).findAny().orElse(null);

    }
    public Employee findById(Long id){
        return employeeRepository.findById(id);
    }

    @Transactional
    public void updatePassword(String password, Long id){
        employeeRepository.updatePassword(password,id);
    }




}
