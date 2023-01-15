package hello.advanced.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepositoryV0 orderRepositoryV0;

    public void orderItem(String itemId){
        orderRepositoryV0.save(itemId);
    }
}
