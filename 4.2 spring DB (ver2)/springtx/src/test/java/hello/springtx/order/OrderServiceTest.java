package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void complete() throws NotEnoughMoneyException {
        //given
        Order order = new Order();
        order.setUserName("정상");
        //when
        orderService.order(order);  //주문하면
        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("완료");
    }

    @Test
    void runtimeEx() throws NotEnoughMoneyException {
        //given
        Order order = new Order();
        order.setUserName("예외");
        //when
        Assertions.assertThatThrownBy(()->orderService.order(order)).isInstanceOf(RuntimeException.class);  //주문하면
        //then
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        assertThat(orderOptional.isEmpty()).isTrue();   //롤백을 하면 DB에 조회가 안되기 때문에 저장소에는 빈값으로 나옴
    }
    @Test
    void bizEx() {
        //given
        Order order = new Order();
        order.setUserName("잔고부족");
        //when
        try {
            orderService.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
        }
        //then
        Order findOrder = orderRepository.findById(order.getId()).get();
        assertThat(findOrder.getPayStatus()).isEqualTo("대기");

    }

}