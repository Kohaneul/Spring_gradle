package haneul.haneulspring.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
    //@MyExcludeComponent 가 붙은 것은 ComponentScan에 제외할 것이다.

}
