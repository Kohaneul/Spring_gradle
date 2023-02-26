package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    /**실제 구현체가 아닌 프록시 객체를 스프링 빈으로 등록해야한다
        OrderRepository(Proxy)->OrderRepositoryImpl(target)->OrderService(Proxy)->OrderServiceImpl(target)->OrderController(Proxy)->OrderControllerImpl(target)
     **/
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){
        OrderControllerV1 controllerImpl=  new OrderControllerV1Impl(orderService(logTrace));   //OrderControllerV1Impl 실제 객체 참조
        return new OrderControllerInterfaceProxy(controllerImpl,logTrace);
    }
    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl,logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace){
        OrderRepositoryV1 repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl,logTrace);
    }





}
