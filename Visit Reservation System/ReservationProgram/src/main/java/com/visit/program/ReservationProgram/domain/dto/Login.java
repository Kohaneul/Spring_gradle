package com.visit.program.ReservationProgram.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class Login {
    @NotEmpty(message="id가 누락되었습니다.")
    private String loginId;
    @NotEmpty(message="패스워드가 누락되었습니다.")
    private String password;


    public Login(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
