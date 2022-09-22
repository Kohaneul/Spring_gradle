package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;


@Getter
@Setter
@NoArgsConstructor
@Embeddable //JPA의 내장타입이라는걸 표시해줌
public class Address {
    private String city;
    private String street;
    private String zipcode;

}
