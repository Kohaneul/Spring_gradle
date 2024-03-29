package hello.advanced.app.v5;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OrderRepositoryV5 {

   private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace logTrace) {
        this.template = new TraceTemplate(logTrace);
    }

    public void save(String itemId){
        template.execute("OrderRepositoryV6.save()",
                () -> {
                    if(itemId.equals("ex")){
                        throw new IllegalStateException("예외 발생!!");
                    }
                    sleep(1000);
                    return null;
                }
        );
    }
    private void sleep(int milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
