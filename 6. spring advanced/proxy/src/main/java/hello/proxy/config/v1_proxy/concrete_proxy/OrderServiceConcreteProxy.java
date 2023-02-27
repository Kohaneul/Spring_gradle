package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2{
    private final OrderServiceV2 orderServiceV2;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 orderServiceV2, LogTrace logTrace) {
        super(null);    //이 과정을 생략하면 자바에서 기본생성자 호출함
        // 부모 클래스 생성자 호출x-> 프록시 역할만 하기 때문에 null로
        this.orderServiceV2 = orderServiceV2;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try{
            status = logTrace.begin("OrderService.orderItem()");
            orderServiceV2.orderItem(itemId);
            logTrace.end(status);

        }
        catch(Exception e){
            logTrace.exception(status,e);
            throw e;
        }

    }
}
