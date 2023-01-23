package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FieldLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();


    @Test
    void begin_end_level2(){
        TraceStatus hello1 = trace.begin("hello1");
        TraceStatus hello2 = trace.begin("hello2");
        trace.end(hello2);
        trace.end(hello1);
    }
    @Test
    void begin_exception_level2(){
        TraceStatus hello1 = trace.begin("hello1");
        TraceStatus hello2 = trace.begin("hello2");
        trace.exception(hello2,new IllegalStateException());
        trace.exception(hello1,new IllegalStateException());

    }

}