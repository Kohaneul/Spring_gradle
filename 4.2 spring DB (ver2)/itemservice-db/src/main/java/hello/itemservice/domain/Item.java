package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity //JPA 객체로 인정이 된다.
public class Item {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)  //GenerationType.IDENTITY : pk 값을 DB에서 생성 , Auto increment와 동일
    private Long id;
    @Column(name="item_name",length=10) //mybatis.configuration.map-underscore-to-camel-case=true 으로 설정했기 떄문에 굳이 없어도 된다.
    private String itemName;
    private Integer price;  //컬럼명과 테이블명이 같으면 @Column은 기재하지 않아도 된다.
    private Integer quantity;

    public Item() {}   //JPA 는 public, protected 타입의 기본생성자 필수!!!!

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
