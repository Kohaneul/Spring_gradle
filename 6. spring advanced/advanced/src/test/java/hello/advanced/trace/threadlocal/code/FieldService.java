package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {
    private String nameStore;
    public String logic(String name){
        log.info("저장 name={} -> nameStore={}",name,nameStore);
        nameStore = name;
        sleep(1000);    //1초간 쉬고
        log.info("조회 nameStore={}" , nameStore);
        return nameStore;   //저장된 nameStore 반환
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
