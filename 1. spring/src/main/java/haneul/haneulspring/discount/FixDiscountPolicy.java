package haneul.haneulspring.discount;

import haneul.haneulspring.member.Grade;
import haneul.haneulspring.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("fixDiscountPolicy")
@Component
public class FixDiscountPolicy implements DiscountPolicy{
    private int discountFicAmount = 1000;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP) {
            return discountFicAmount;
        }
        else {
            return 0;
        }
    }
}
