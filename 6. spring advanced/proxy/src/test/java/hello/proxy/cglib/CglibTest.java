package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
@Slf4j
public class CglibTest {

    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();
        Enhancer enhancer = new Enhancer(); //cglib를 만드는 코드
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService)enhancer.create();//proyx 생성
        log.info("target Class={}",target.getClass());
        log.info("proxy Class={}",proxy.getClass());

        proxy.call();

    }

}
