package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.UpdateVisitor;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface VisitorRepository {
    List<Visitor> findAll();
    Visitor findOne(Long id);

    Long saveInfo(Visitor visitor);
    void deleteInfo(Long id);
    void updateCheckedInfo(@Param("id")Long id);

    void updateInfo(UpdateVisitor updateVisitor);

}
