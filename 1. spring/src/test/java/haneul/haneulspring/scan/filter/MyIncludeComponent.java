package haneul.haneulspring.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
    //@MyIncludeComponent 가 붙은 것은 ComponentScan에 추가할 것이다.
}
