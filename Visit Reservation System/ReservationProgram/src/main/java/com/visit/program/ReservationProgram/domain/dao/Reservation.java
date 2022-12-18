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
    private String visitor;
    private String company_person;
    private String visit_Date1;
    private String visit_Date2;
    private String phone_number;
    private String birth;
    private String company;
    private boolean is_checked;
    public Reservation(Long visitor_id, String visitor,String company_person,String visit_Date1,String visit_Date2,String phone_number,String birth,String company, Boolean is_checked){
       this.visitor_id = visitor_id;
        this.visitor = visitor;
       this.company_person = company_person;
       this.visit_Date1 = visit_Date1;
       this.visit_Date2 = visit_Date2;
       this.phone_number = phone_number;
       this.birth = birth;
       this.company =company;
       this.is_checked = is_checked;
    }


}
