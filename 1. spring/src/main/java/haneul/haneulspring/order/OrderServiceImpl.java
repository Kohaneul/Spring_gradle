package haneul.haneulspring.order;

import haneul.haneulspring.annotation.MainDiscountPolicy;
import haneul.haneulspring.discount.DiscountPolicy;
import haneul.haneulspring.discount.FixDiscountPolicy;
import haneul.haneulspring.discount.RateDiscountPolicy;
import haneul.haneulspring.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*IoC 컨테이너의 등록방식
    1. Component 를 통한 컨테이너 등록
    2. Autowired 을 통한 의존관계 주입
            의존관계 주입방식 : setter주입(선택적, 변경가능할때) , 생성자 주입(불변)

*/
@Component
 // final 생성자를 자동으로 생성해줌
public class OrderServiceImpl implements OrderService{
    //생성자주입 : 불변, 누락을 막을 수 있다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;//인터페이스에만 의존하는 중


    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // @Qualifier : 추가 구분자 bean이 두개일때 설정해둔 이름으로 찾음
    //
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }


//
//    @Autowired
//    //  생성자가 딱 하나만 있으면 Autowired가 생략해도 된다 => 자동으로 의존관계 주입
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

//    메서드 주입 (보통 생성자 주입에서 대부분 해결하므로 거의 사용하지 않는다)
//    @Autowired
//    public void init(MemberRepository repository, DiscountPolicy discountPolicy){
//        this.repository= repository;
//        this.discountPolicy = discountPolicy;
//    }
    //


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);//최종적으로 할인가격 받음

        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
