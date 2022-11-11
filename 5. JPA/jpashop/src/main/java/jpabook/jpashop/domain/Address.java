package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable //JPA의 내장타입이라는걸 표시해줌
@AllArgsConstructor
public class Address {

    @Column(length=10)
    private String city;
    @Column(length=20)
    private String street;
    @Column(length=5)
    private String zipcode;


     protected Address(){

     }

     private String fullAddress(){
         return getCity()+" " +getStreet()+" " +getZipcode();
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
