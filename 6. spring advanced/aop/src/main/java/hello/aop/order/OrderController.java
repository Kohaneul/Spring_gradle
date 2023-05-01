package hello.aop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public String request(String itemId){
        orderService.order(itemId);
        return itemId;
    }

}
