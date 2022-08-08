package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;   //hibernate validator에서만 동작함

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    private Long id;
    @NotBlank(message="공백 x ")  //메시지 변경 가능
    private String itemName;
    @NotNull
    @Range(min=1000,max=1000000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
