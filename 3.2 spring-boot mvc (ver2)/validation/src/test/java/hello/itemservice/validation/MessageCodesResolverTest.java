package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.*;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
    //에러코드를 넣으면 여러개의 값들을 반환함

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
      //  new ObjectError("item",new String[]{"required.item","required"}); ObjectError가 messageCodes에 있는 key값을 담아준다.
        assertThat(messageCodes).contains("required.item","required");
    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        //실제 작동 :     new FieldError("item","itemName",null,false,messageCodes,null,null);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName","required.itemName","required.java.lang.String","required"
        );
    }
}
