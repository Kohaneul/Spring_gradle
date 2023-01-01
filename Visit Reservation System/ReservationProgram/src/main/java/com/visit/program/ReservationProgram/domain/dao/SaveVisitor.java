package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaveVisitor extends PropertyEditorSupport {

    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$")
    private String phone_number;
    @NotEmpty
    private String company;
    @NotEmpty
    private String visit_date1;
    @NotEmpty
    private String visit_date2;
    @NotEmpty
    private String birth;
    @NotEmpty
    private String purpose;
    private String write_date;
    private int count;
    private String revised_write_date;
    private Boolean is_checked;

    public SaveVisitor(String employee_name, String name, String phone_number, String company,String visit_date1, String visit_date2,String birth, String purpose) {
        this.employee_name = employee_name;
        this.name = name;
        this.phone_number = phone_number;
        this.company = company;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.birth = birth;
        this.purpose = purpose;
        this.count = 0;
        this.is_checked = false;
        this.revised_write_date = null;
    }
}
