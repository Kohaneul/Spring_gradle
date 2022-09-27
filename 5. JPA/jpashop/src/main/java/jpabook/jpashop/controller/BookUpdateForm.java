package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookUpdateForm {
    @NotEmpty(message = "아이디값은 필수입니다.")
    private Long id;
    @NotEmpty(message = "상품 이름은 필수입니다.")
    private String name;
    @NotNull(message = "상품 가격 입력은 필수입니다.")
    private Integer price;
    @NotNull(message = "상품 재고 입력은 필수입니다.")
    private Integer stockQuantity;
    @NotEmpty(message = "저자 입력은 필수입니다.")
    private String author;
    @NotEmpty(message = "ISBN 입력은 필수입니다.")
    private String isbn;

}
