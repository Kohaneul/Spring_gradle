package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;   //hibernate validator에서만 동작함
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
//@ScriptAssert(lang="javascript",script="_this.price * _this.quantity >= 10000", message="총 합이 10000원 넘게 입력해주세요.") <- 기능이 약해서 권장하지 않음
public class Item {
    //@NotNull(groups=UpdateCheck.class)  //수정 요구사항 추가
    private Long id;
    //@NotBlank(groups = {SaveCheck.class,UpdateCheck.class})  //메시지 변경 가능
    private String itemName;
    //@NotNull(groups = {SaveCheck.class,UpdateCheck.class})
    //@Range(min=1000,max=100000,groups = {SaveCheck.class,UpdateCheck.class})
    private Integer price;  //숫자타입만 들어가야하는데 A라는 문자가 들어간다면 typeMismatch 에러가 뜸(errors.properties에서 설정한 내용으로 뜸)
    //@NotNull(groups = {SaveCheck.class,UpdateCheck.class})
    //@Max(value=9999,groups={SaveCheck.class})  //수정할때는 수량 무제한으로 변경 가능하므로..
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
