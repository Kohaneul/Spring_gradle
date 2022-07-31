package haneul.haneulspring.web;

import haneul.haneulspring.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service    //웹 관련한 부분은 Controller에 구현. service에서는 순수 코드로 구현
@RequiredArgsConstructor
public class LogDemoService {
    private final MyLogger myLogger;

    public void logic(String id){
        myLogger.log("service id = "+id);
    }
}
