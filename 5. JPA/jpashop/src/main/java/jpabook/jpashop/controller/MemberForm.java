package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
//Member Entity를 사용하지 않았다. 이유 : 순수한 비즈니스 로직을 지켜주어야 하기 때문. 확장시 유지보수에도 용이
//대신 화면에 보여지는 전용의 해당 객체를 생성해주었음.
@Getter
@Setter
public class MemberForm {
    @NotEmpty(message="회원 이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
