package hello.advanced.app.v5;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final TraceTemplate template;
    private final OrderServiceV5 orderServiceV5;

    public OrderControllerV5(LogTrace logTrace, OrderServiceV5 orderServiceV5) {
        this.template = new TraceTemplate(logTrace);    //생성자에서 한번만 호출(싱글톤) (or template 을 스프링 빈으로 등록하는 방법도 있음)
        this.orderServiceV5 = orderServiceV5;
    }

    @GetMapping("/v5/request")
    public String request(String itemId){
     return   template.execute("OrderControllerV5.request()", () -> {
            orderServiceV5.orderItem(itemId);
            return "ok";
        });

        }
}
