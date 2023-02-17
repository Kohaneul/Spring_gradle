package hello.advanced;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean   //수동bean 등록
    public LogTrace LogTraceConfig() {
//        return new FieldLogTrace();
        return new ThreadLocalLogTrace();
    }

}
