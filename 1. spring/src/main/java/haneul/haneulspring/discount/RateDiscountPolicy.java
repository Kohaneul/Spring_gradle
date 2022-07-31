package haneul.haneulspring.discount;

import haneul.haneulspring.annotation.MainDiscountPolicy;
import haneul.haneulspring.member.Grade;
import haneul.haneulspring.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary    // primary : 빈이 두개 이상일때 우선순위로 잡히고 의존관계 주입

@MainDiscountPolicy
@Qualifier("rateDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy{
        //return 할인 대상 금액
    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade()==Grade.VIP) {
            return price*discountPercent/100;
        } else {
            return 0;
        }
    }
}
