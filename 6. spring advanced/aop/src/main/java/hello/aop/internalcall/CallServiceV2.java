package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {
//    private final ApplicationContext ac;
//  public CallServiceV2(ApplicationContext applicationContext) {
//      this.ac = applicationContext;
//  }
    private final ObjectProvider<CallServiceV2> callServiceV2Provider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2Provider) {
        this.callServiceV2Provider = callServiceV2Provider;
    }


    public void external(){
        log.info("call external");
       // CallServiceV2 callServiceV2 = ac.getBean(CallServiceV2.class);  //지연 호출
        CallServiceV2 callServiceV2 = callServiceV2Provider.getObject();//지연 호출 (실제 자기자신을 사용하는 시점에서 호출)
        callServiceV2.internal();   //외부메서드 호출
    }

    public void internal(){
        log.info("call internal");
    }
}
