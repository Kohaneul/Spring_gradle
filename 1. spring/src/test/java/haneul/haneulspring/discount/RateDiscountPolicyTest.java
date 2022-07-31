package haneul.haneulspring.discount;

import haneul.haneulspring.member.Grade;
import haneul.haneulspring.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o(){
        //given
        Member memb1 = new Member(1L,"g1",Grade.VIP);
        Member memb2 = new Member(3L,"g2",Grade.BASIC);
        //when
        int discount = discountPolicy.discount(memb1,10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }
    @Test
    @DisplayName("VIP가 아닌 경우 0% 할인이 적용되어야 한다.")
    void vip_x(){
        Member basic = new Member(1L,"g1",Grade.BASIC);
        int discount = discountPolicy.discount(basic,1000);
        Assertions.assertThat(discount).isEqualTo(0);
    }
}