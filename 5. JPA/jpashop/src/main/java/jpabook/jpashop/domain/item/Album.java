package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;


}
