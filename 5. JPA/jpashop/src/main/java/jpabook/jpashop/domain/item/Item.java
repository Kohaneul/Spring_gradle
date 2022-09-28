package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   //한테이블에 몰아서
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<>();

    //비즈니스 로직 : @Setter를 넣는 대신 비즈니스 로직이 필요하다면 하기와 같이 메소드를 직접 만들어서 넣기

    //재고증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //재고 감소
    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        zeroException(restStock);
        this.stockQuantity=restStock;
    }

    private void zeroException(int restStock) {
        if(restStock<0){
            throw new NotEnoughStockException("need more stock!!!");
        }

    }
    public void change(int price,String name,int stockQuantity){
        this.setPrice(price);
        this.setName(name);
        this.setStockQuantity(stockQuantity);
    }



}
