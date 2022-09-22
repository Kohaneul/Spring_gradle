package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Getter @Setter
@DiscriminatorValue("B")
@Entity
public class Book extends Item {

    private String author;
    private String isbn;


}
