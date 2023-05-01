package hello.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepository {
    public void save(String itemId){
        if(itemId.equals("ex")){
            throw new IllegalStateException("아이디 입력 오류");
        }
        sleep(1000);
    }


    private void sleep(int milliSc){
        try {
            Thread.sleep(milliSc);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
