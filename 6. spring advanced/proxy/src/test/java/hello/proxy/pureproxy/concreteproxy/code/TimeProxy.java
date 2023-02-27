package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic{
    private ConcreteLogic concreteLogic;    //실제 호출 대상을 받음

    public TimeProxy(ConcreteLogic concreteLogic) {
        this.concreteLogic = concreteLogic;
    }

    @Override
    public String operation() {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        String operation = concreteLogic.operation();

        long result = System.currentTimeMillis() - startTime;
        log.info("TimeProxy 종료 resultTime={} , {}",result,operation);

        return operation;
    }
}
