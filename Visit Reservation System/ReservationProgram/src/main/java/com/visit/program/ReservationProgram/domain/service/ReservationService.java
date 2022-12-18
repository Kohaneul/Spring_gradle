package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.ReservationInfo;
import com.visit.program.ReservationProgram.domain.dao.SaveReservationInfo;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import com.visit.program.ReservationProgram.domain.repository.ReservationRepository;
import com.visit.program.ReservationProgram.domain.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final VisitorRepository visitorRepository;

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public List<Reservation>findAllPages(int start, int end){
        return reservationRepository.findAllPages(start,end);
    }

    @Transactional
    public void updateCheckedInfo(Long id){
        Visitor visitor = visitorRepository.findOne(id);
        reservationRepository.updateCheckedInfo(visitor.is_checked(),id);
    }
    @Transactional
    public void saveInfo(SaveReservationInfo saveReservationInfo){
        reservationRepository.saveInfo(saveReservationInfo);
    };





    @Transactional
    public void deleteInfo(Long id){
        reservationRepository.deleteInfo(id);
    };

    public ReservationInfo findInfo(Long id){
        return reservationRepository.findInfoOne(id);
    }

    public Reservation findOne(Long id){
        return reservationRepository.findOne(id);
    }



}
