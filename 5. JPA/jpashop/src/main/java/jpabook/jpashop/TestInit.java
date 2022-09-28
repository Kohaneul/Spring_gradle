package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestInit {
    @Autowired private final ItemService itemService;
    @Autowired  private final MemberService memberService;
    @Autowired  private final OrderService orderService;

    @PostConstruct
    public void testInit(){
        Book book = new Book();
        book.setPrice(1000);
        book.setIsbn("11111");
        book.setAuthor("김영한");
        book.setPrice(10000);
        book.setName("JPA 개발자");
        book.setStockQuantity(111);

        Book book1 = new Book();
        book1.setPrice(2000);
        book1.setIsbn("2222");
        book1.setAuthor("김영한2");
        book1.setPrice(20000);
        book1.setName("JPA 개발자2");
        book1.setStockQuantity(222);

        Member memberA = new Member();
        memberA.setName("memberA");
        memberA.setAddress(new Address("seoul","seongsu","1157"));

        Member memberB = new Member();
        memberB.setName("memberB");
        memberB.setAddress(new Address("seoul","gangnam","5487"));

        memberService.join(memberA);
        memberService.join(memberB);
        itemService.saveItem(book);
        itemService.saveItem(book1);


        Delivery delivery = new Delivery();
        delivery.setAddress(memberA.getAddress());
        orderService.order(memberA.getId(),book1.getId(),10);
        orderService.order(memberB.getId(),book1.getId(),20);


    }
}
