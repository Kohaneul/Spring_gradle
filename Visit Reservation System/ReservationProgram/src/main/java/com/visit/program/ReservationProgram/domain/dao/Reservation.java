package com.visit.program.ReservationProgram.domain.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Long visitor_id;
    private String name;
    private String employee_name;
    private String visit_Date1;
    private String visit_Date2;
    private String phone_number;
    private String birth;
    private String company;
    private Boolean is_checked;
    public Reservation(Long id, String name, String employee_name,String visit_Date1,String visit_Date2,String phone_number,String birth,String company, Boolean is_checked,Long visitor_id){
           this.id = id;
           this.name = name;
           this.employee_name = employee_name;
           this.visit_Date1 = visit_Date1;
           this.visit_Date2 = visit_Date2;
           this.phone_number = phone_number;
           this.birth = birth;
           this.company =company;
           this.is_checked = is_checked;
           this.visitor_id = visitor_id;
    }


}
