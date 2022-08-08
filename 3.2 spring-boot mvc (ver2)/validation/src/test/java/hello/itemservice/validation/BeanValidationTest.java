package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation(){
        //검증기 생성 => factory 가져옴
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        //factory에서 validator 만드는 것
        Validator validator = factory.getValidator();
        Item item = new Item();
        item.setItemName(" ");  //공백
        item.setPrice(0);
        item.setQuantity(100000000);

        //해당 값은 설정값의 범위에 초과되므로 오류가 떠야 함 => 검증해야함

        //검증
        Set<ConstraintViolation<Item>> violations = validator.validate(item);//validator에 item을 넣는다
        //결과가 비어있으면 검증오류가 없는 것
        for (ConstraintViolation<Item> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}
