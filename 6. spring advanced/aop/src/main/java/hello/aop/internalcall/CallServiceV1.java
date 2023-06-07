package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {
    private CallServiceV1 callServiceV1;    //자기자신을 가지고 있음
    //객체 생성하기 전 이어서 생성자 주입은 안된다 -> 순환참조 문제 발생함
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}",callServiceV1.getClass());
        this.callServiceV1 = callServiceV1; //객체 생성 후 setter 생성하기 때문에 setter 메서드를 사용하는것이 적절
    }

    public void external(){ //aop가 찍히고 해당 메서드 호출
        log.info("call external");
        callServiceV1.internal(); //프록시 인스턴스를 통해 호출
    }

    public void internal(){
        log.info("call internal");
    }
}
