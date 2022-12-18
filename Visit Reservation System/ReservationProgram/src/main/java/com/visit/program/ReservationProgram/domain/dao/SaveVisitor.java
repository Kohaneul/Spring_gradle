package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaveVisitor {

    @NotNull
    private Long employee_id;
    @NotEmpty
    private String name;

    @NotEmpty
    private String phone_number;
    @NotEmpty
    private String company;

    @NotNull
    @Range(min = 1,max = 12,message = "[월 입력 오류] 1~12 사이의 숫자로 입력해주세요")
    private Integer month;
    @NotNull
    @Range(min = 1,max = 31,message = "[일 입력 오류] 1~31 사이의 숫자로 입력해주세요")
    private Integer day;
    @NotNull
    @Range(min = 1,max = 24,message = "[시간 입력 오류] 1~24 사이의 숫자로 입력해주세요")
    private Integer hour;
    @NotNull
    @Range(min = 0,max = 60,message = "[분 입력 오류 ]0~60 사이의 숫자로 입력해주세요")
    private Integer minute;



    @Range(min = 1,max = 12,message = "[월 입력 오류] 1~12 사이의 숫자로 입력해주세요")
    private Integer month1;
    @Range(min = 1,max = 31,message = "[일 입력 오류] 1~31 사이의 숫자로 입력해주세요")
    private Integer day1;
    @Range(min = 1,max = 24,message = "[시간 입력 오류] 1~24 사이의 숫자로 입력해주세요")
    private Integer hour1;
    @Range(min = 0,max = 60,message = "[분 입력 오류]0~60 사이의 숫자로 입력해주세요")
    private Integer minute1;


    @NotEmpty
    private String birth;
    @NotEmpty
    @Length(min = 4, max = 30,message = "글자수는 최소 4자에서 30자 까지 입니다.")
    private String purpose;

    private String revised_write_date;

    public SaveVisitor(Long employee_id, String name, String phone_number, String company,Integer month,Integer day,Integer hour,Integer minute,
            Integer month1,Integer day1,Integer hour1,Integer minute1,String birth, String purpose) {
        this.employee_id = employee_id;
        this.name = name;
        this.phone_number = phone_number;
        this.company = company;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

        this.month1 = month1;
        this.day1 = day1;
        this.hour1 = hour1;
        this.minute1 = minute1;
        this.birth = birth;
        this.purpose = purpose;
        this.revised_write_date = null;
    }
}
