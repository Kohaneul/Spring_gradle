package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded   //내장타입을 포함했다
    private Address address;

    @JsonIgnore //양방향 연관관계시 한쪽을 JsonIgnore 로 끊어줌
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();



}
