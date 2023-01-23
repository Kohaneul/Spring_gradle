package hello.advanced.app.v2;
import hello.advanced.trace.HelloTraceV2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId){
        TraceStatus status =null;
        TraceStatus status1 = null;
        try{
            status = trace.begin("OrderControllerV2.request().begin");  //TraceStatus 객체 생성->id 값 넘겨받을 예정
            status1 = trace.beginSync(status.getTraceId(), "OrderControllerV2.request()");
            orderService.orderItem(status1.getTraceId(),itemId);
            trace.end(status1);
            trace.end(status);
            return "ok";

        }
        catch(Exception e){
            trace.exception(status1,e);
            trace.exception(status,e);
            throw e;    //예외를 꼭 다시 던져주어야 한다.
        }


    }


}
