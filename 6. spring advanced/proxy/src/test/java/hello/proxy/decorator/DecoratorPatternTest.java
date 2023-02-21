package hello.proxy.decorator;

import hello.proxy.decorator.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {
    //데코레이터 패턴의 특징 : 클라이언트는 기존 코드를 변경하지 않고 프록시 객체를 계속 생성하여 꾸밈
      @Test
    void noDecorator(){
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
      }

      @Test
    void decorator1(){
          Component realComponent = new RealComponent();
          Component messageDecorator = new MessageDecorator(realComponent);
          DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
          client.execute();
      }

    @Test
    void decorator2(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
