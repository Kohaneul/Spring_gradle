package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.dto.VisitorSearchDTO;
import com.visit.program.ReservationProgram.domain.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public List<Visitor> findAll(){
        return visitorRepository.findAll();
    }

    public List<Visitor> findAllDTO(VisitorSearchDTO visitorSearchDTO) {
        return visitorRepository.findAllDTO(visitorSearchDTO);
    }

    public Visitor findOne(Long id) {
        return visitorRepository.findOne(id);
    }

    //정보 저장
    @Transactional
    public Long saveInfo(SaveVisitor saveVisitor) {
        Visitor visitor = getVisitor(saveVisitor);
        visitorRepository.saveInfo(visitor);
        return visitor.getId();
    }

    @Transactional
    public void updateCheckedInfo(Long id){
        Visitor visitor = findOne(id);
        boolean checked = visitor.is_checked();
        checked = checked==true ? false:true;
        visitorRepository.updateCheckedInfo(checked,id);

    }

    private Visitor getVisitor(SaveVisitor visitor) {
        String writeDate = getNowDate();
        String visitDate1 = setDate(visitor.getMonth(),visitor.getDay(), visitor.getHour(), visitor.getMinute());
        String visitDate2 = setDate(visitor.getMonth1(),visitor.getDay1(), visitor.getHour1(), visitor.getMinute1());

        return new Visitor(visitor.getEmployee_id(), visitor.getName(), visitor.getPhone_number(), visitor.getCompany(),visitDate1,visitDate2
                , visitor.getBirth(), visitor.getPurpose(),writeDate,visitor.getRevised_write_date(),0,false);
    }

    @Transactional
    public void deleteInfo(Long id) {
        visitorRepository.deleteInfo(id);
    }


    private String getNowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));
    }


    private String setDate(Integer month, Integer day, Integer hour, Integer minute){
        return month+"월"+ day+"일 "+ hour+"시"+ minute+"분";
    }
    //정보 수정
    @Transactional
    public void updateInfo(UpdateVisitor updateVisitor) {

        String revisedDate = getNowDate();
        String visitDate1 = setDate(updateVisitor.getMonth(),updateVisitor.getDay(), updateVisitor.getHour(), updateVisitor.getMinute());
        String visitDate2 = setDate(updateVisitor.getMonth1(),updateVisitor.getDay1(), updateVisitor.getHour1(), updateVisitor.getMinute1());
        int count =   updateVisitor.getCount()+1;
        visitorRepository.updateInfo(updateVisitor.getEmployee_id(),
                updateVisitor.getName(), updateVisitor.getPhone_number(), updateVisitor.getCompany(), visitDate1
                ,visitDate2,updateVisitor.getBirth(),updateVisitor.getPurpose(),updateVisitor.getWrite_date(),revisedDate,
                count, updateVisitor.is_checked(),updateVisitor.getId());
    }






}
