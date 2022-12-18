package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.ReservationInfo;
import com.visit.program.ReservationProgram.domain.dao.SaveReservationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReservationRepository {
    List<Reservation> findAll();

    void saveInfo(SaveReservationInfo reservation);

    void deleteInfo(Long id);
    void updateCheckedInfo(@Param("is_checked")Boolean is_checked, @Param("id") Long id);
    List<Reservation> findAllPages(@Param("start")int start, @Param("end")int end);

    Reservation findOne(Long id);

    ReservationInfo findInfoOne(Long id);

    List<Reservation> findAllDTO(@Param("employee_id")int employee_id,@Param("is_checked") boolean is_checked);
}
