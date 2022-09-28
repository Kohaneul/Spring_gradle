package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired EntityManager em;

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class,1L);

        //트랜잭션
        book.setName("name A");
        //변경감지(더티체킹): JPA 에서 관리되는 영속성 컨텍스트는 커밋 시점이 되면 JPA 가 변경분을 UPDATE 쿼리 자동으로 생성해서 DB에 반영
    }

}
