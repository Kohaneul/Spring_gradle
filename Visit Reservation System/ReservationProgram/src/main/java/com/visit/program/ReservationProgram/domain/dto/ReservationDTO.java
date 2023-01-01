package com.visit.program.ReservationProgram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    @Nullable
    private String date1;
    @Nullable
    private String date2;
    @Nullable
    private Boolean is_checked;




}
