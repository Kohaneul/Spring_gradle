package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void templateMethod(){
        logic1();
        logic2();

    }

    private void logic1(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 1 실행");

        //비즈니스 로직 종료
        long emdTime = System.currentTimeMillis();
        long resultTime = emdTime - startTime;
        log.info("resultTime={}",resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직 2 실행");

        //비즈니스 로직 종료
        long emdTime = System.currentTimeMillis();
        long resultTime = emdTime - startTime;
        log.info("resultTime={}",resultTime);
    }


    /**
     * 템플릿 메서드 패턴 적용
     * */

    @Test
    void templateMethodV1(){
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    @Test
    void templateMethodV2(){
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };
        template1.execute();

        log.info("클래스이름={}",template1.getClass());


        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 2 실행");
            }
        };
        template2.execute();
        log.info("클래스이름={}",template2.getClass());

    }

    /**
     * 전략 패턴 사용
     * */
    @Test
    void strategy(){
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        contextV2.execute();
    }

    @Test
    void strategyV2(){
        Strategy strategy = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직1 실행 웅앵");
            }
        };
        ContextV1 contextV1 = new ContextV1(strategy);
        log.info("strategyLogic1={}",strategy.getClass());

        contextV1.execute();

        Strategy strategy1= new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직2 실행 웅앵");

            }
        };
        ContextV1 contextV2 = new ContextV1(strategy1);
        log.info("strategyLogic2={}",strategy1.getClass());
        contextV2.execute();
    }

    @Test
    void strategyV3(){

        ContextV1 contextV1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직1 실행 웅앵");
            }
        });

        contextV1.execute();

        ContextV1 contextV2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스로직2 실행 웅앵");

            }
        });
        contextV2.execute();
    }

    //람다 실행 : 인터페이스에 메서드 1개만 있다는 전제하에서 가능
    @Test
    void strategyV4(){
        ContextV1 contextV1 = new ContextV1(()->log.info("비즈니스 로직1 실행"));
        contextV1.execute();
        ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직 2 실행"));
        contextV2.execute();
    }

}
