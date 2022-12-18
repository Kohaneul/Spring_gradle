package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dao.SaveEmployee;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import com.visit.program.ReservationProgram.domain.dto.EmployeeSearchDTO;
import com.visit.program.ReservationProgram.domain.dto.VisitorSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EmployeeRepository {

    List<Employee> findAll();
    Employee findOne(Long id);
    List<Employee> findAllDTO(EmployeeSearchDTO employeeSearchDTO);

    void saveInfo(SaveEmployee saveEmployee, String date);
    void deleteInfo(Long id);
    void updateInfo(String name, String department,String email,char gender,Long id);

}
