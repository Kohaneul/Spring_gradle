package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.UpdateVisitor;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import com.visit.program.ReservationProgram.domain.dto.VisitorSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Mapper
public interface VisitorRepository {

    List<Visitor> findAll();
    List<Visitor> findAllDTO(VisitorSearchDTO visitorSearchDTO);

    Visitor findOne(Long id);

    void saveInfo(Visitor visitor);
    void deleteInfo(Long id);
    void updateCheckedInfo(@Param("is_checked")Boolean is_checked,@Param("id")Long id);


    void updateInfo(@Param("employee_id") Long employee_id, @Param("name")String name,
                    @Param("phone_number") String phone_number,@Param("company")String company, @Param("visit_date1") String visit_date1,
                    @Param("visit_date2") String visit_date2,@Param("birth") String birth,
                    @Param("purpose") String purpose,@Param("write_date")String write_date,
                    @Param("revised_write_date") String revised_write_date, @Param("count") int count, @Param("is_checked") boolean is_checked,@Param("id") Long id);




}
