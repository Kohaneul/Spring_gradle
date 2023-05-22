package hello.aop.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository repository;
    public void orderItem(String itemId){
        log.info("[orderService] 실행");
        repository.save(itemId);
    }
}
