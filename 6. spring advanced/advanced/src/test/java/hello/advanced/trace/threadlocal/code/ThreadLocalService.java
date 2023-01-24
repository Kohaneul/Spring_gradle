package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    private ThreadLocal<String> nameStore = new ThreadLocal<>();
    //ThreadLocal : 스레드 저장소
    //set : 값 저장
    //get : 값 조회
    //remove : 값 지움

    public String logic(String name){
        log.info("저장 name={} -> nameStore={}",name,nameStore.get());
        nameStore.set(name);
        sleep(1000);    //1초간 쉬고
        log.info("조회 nameStore={}" , nameStore.get());
        return nameStore.get();   //저장된 nameStore 반환
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
