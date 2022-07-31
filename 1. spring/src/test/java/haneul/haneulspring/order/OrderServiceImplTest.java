package haneul.haneulspring.order;

import haneul.haneulspring.discount.FixDiscountPolicy;
import haneul.haneulspring.discount.RateDiscountPolicy;
import haneul.haneulspring.member.Grade;
import haneul.haneulspring.member.Member;
import haneul.haneulspring.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Test
    void createOrder(){
        MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();
        memoryMemberRepository.save(new Member(1L,"name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(),new RateDiscountPolicy());
        Order itemA = orderService.createOrder(1L, "itemA", 10000);
        assertThat(itemA.getDiscountPrice()).isEqualTo(1000);
    }
}