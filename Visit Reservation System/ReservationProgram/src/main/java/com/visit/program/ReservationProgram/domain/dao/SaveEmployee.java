package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveEmployee {
    @NotEmpty
    private String name;
    @NotEmpty
    private String department;
    @NotEmpty
    private String email;
    @NotEmpty
    private char gender;



}
