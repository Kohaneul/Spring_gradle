package jpabook.jpashop.ex;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class BookStore {
    @Id
    @GeneratedValue
    @Column(name = "bookStore_id")
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "bookStore")
    private Set<Book1> books = new HashSet<>();

    public void setBooks(Book1 book) {
        book.setBookStore(this);
        books.add(book);
    }
}
