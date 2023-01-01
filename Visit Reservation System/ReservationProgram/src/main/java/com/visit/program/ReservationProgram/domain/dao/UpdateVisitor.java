package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateVisitor {
    @NotNull
    private Long id;
    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String name;
    @NotEmpty
    private String purpose;
    @NotEmpty
    private String company;
    @NotEmpty
    private String phone_number;
    @NotEmpty
    private String visit_date1;
    @NotEmpty
    private String visit_date2;
   @NotEmpty
   private String write_date;
   @NotEmpty
    private String revised_write_date;
    @NotEmpty
    private String birth;
    @NotNull
    private Integer count;
    @NotNull
    private Boolean is_checked;
    public UpdateVisitor(String employee_name, String name, String phone_number, String company, String visit_date1,
                         String visit_date2, String birth, String purpose, String write_date, Integer count, Boolean is_checked,Long id) {
        this.employee_name = employee_name;
        this.id =id;
        this.name = name;
        this.purpose = purpose;
        this.company = company;
        this.phone_number = phone_number;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.write_date = write_date;
        this.revised_write_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));
        this.birth = birth;
        this.count =count;
        this.is_checked = is_checked;
    }
}
