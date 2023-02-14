package hello.advanced.trace.strategy;


import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {
    /**
     * 전략 패턴 사용
     */
    @Test
    void strategyV1(){
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }


    /**
     * 전략 패턴 사용_익명 내부클래스
     */
    @Test
    void strategyV2(){
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행!");
            }
        });

        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행!");
            }
        });

    }

    /**
     * 전략 패턴 사용_익명 내부클래스+람다
     */
    @Test
    void strategyV3(){
        ContextV2 context = new ContextV2();
        context.execute(()->log.info("비즈니스 로직 1 실행"));
        context.execute(() -> log.info("비즈니스 로직 2 시행"));
    }


}
