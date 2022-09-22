package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CategoryItem {

    @ManyToOne
    @JoinColumn(name="category_id")
    private Long categoryId;
    @ManyToOne
    @Column(name="item_id")
    private Long itemId;
}
