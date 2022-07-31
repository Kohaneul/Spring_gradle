package hello.itemservice.domain.item;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Item {
    private Long id;
    private String itemName;
    private Integer price;  //price가 안들어갈때도 가정함
    private Integer quantity;  //qty가 안들어갈때도 가정함 <== int가 쓰게 되면 0으로 넣어줘야하기때문에 Integer로

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
