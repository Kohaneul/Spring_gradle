package jpabook.jpashop.ex;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book1Repository extends JpaRepository<Book1,Long> {
}
