package hello.aop.order;

import hello.aop.order.OrderRepository;
import hello.aop.order.OrderService;
import hello.aop.order.aop.AspectV1;
import hello.aop.order.aop.AspectV2;
import hello.aop.order.aop.AspectV3;
import hello.aop.order.aop.AspectV4Pointcut;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class) //spring bean 등록
//@Import(AspectV2.class) //spring bean 등록
//@Import(AspectV3.class) //spring bean 등록
@Import(AspectV4Pointcut.class) //spring bean 등록
public class AopTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    void aopInfo(){
        log.info("isAopProxy, orderRepository={}",AopUtils.isAopProxy(orderRepository));
        log.info("isAopProxy, orderService={}",AopUtils.isAopProxy(orderService));
    }

    @Test
    void success(){
        orderService.orderItem("itemA");
    }

    @Test
    void exception(){
        Assertions.assertThatThrownBy(()->orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }




}
