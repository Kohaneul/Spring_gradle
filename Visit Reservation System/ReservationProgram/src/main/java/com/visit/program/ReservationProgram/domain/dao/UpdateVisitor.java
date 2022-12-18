package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVisitor {
    @NotNull
    private Long id;
    @NotNull
    private Long employee_id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String purpose;
    @NotEmpty
    private String company;
    @NotEmpty
    private String phone_number;

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
    @Range(min = 0,max = 60,message = "[분 입력 오류 ]0~60 사이의 숫자로 입력해주세요")
    private Integer minute1;

   @NotEmpty
   private String write_date;
    @NotEmpty
    private String birth;
    @NotNull
    private Integer count;
    @NotNull
    private boolean is_checked;



}
