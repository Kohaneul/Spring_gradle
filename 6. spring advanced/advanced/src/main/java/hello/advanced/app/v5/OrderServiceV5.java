package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceV5 {
    private final OrderRepositoryV5 repositoryV5;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 repositoryV5, LogTrace logTrace) {
        this.repositoryV5 = repositoryV5;
        this.template = new TraceTemplate(logTrace);
    }

    public void orderItem(String itemId){
        template.execute("OrderServiceV5.save()", () -> {
         repositoryV5.save(itemId);
        return null;
        });
    }

}
