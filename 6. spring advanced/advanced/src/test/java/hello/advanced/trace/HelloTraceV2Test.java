package hello.advanced.trace;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloTraceV2Test {

    @Test
    void begin_end(){
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.end(status2);
        trace.end(status1);
    }
    @Test
    void begin_exception(){
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus hello1 = trace.begin("hello1");
        TraceStatus hello2 = trace.beginSync(hello1.getTraceId(), "hello2");
        trace.exception(hello2,new RuntimeException());
        trace.exception(hello1,new RuntimeException());

    }

}