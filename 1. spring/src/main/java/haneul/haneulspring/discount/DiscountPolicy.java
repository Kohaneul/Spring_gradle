package haneul.haneulspring.discount;

import haneul.haneulspring.member.Member;

public interface DiscountPolicy {
    //@Return 할인 대상 금액
    int discount(Member member, int price);
}
