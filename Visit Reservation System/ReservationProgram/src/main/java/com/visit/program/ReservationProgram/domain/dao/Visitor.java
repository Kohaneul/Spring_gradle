package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Visitor {
    private Long id;
    private Long employee_id;
    private String name;
    private String phone_number;
    private String visit_date1;
    private String visit_date2;
    private String company;
    private String birth;
    private String purpose;
    private String write_date;
    private String revised_write_date;
    private int count;
    private boolean is_checked;
    public Visitor(Long employee_id,String name,String phone_number, String company,String visit_date1, String visit_date2,String birth,String purpose, String write_date,
                   String revised_write_date, int count,boolean is_checked){
        this.employee_id  = employee_id;
        this.name = name;
        this.phone_number = phone_number;
        this.company = company;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.birth = birth;
        this.purpose = purpose;
        this.write_date = write_date;
        this.revised_write_date = revised_write_date;
        this.count = count;
        this.is_checked = is_checked;

    }


}
