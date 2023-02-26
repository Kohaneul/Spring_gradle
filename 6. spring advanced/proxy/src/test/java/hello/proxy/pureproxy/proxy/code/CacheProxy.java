package hello.proxy.pureproxy.proxy.code;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CacheProxy implements Subject {

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
      log.info("프록시 호출");
      if(cacheValue==null){     //cacheValue에 값이 있으면 RealSubject 호출
          cacheValue = target.operation();  //첫번재 호출일때는 null  떄문에 RealSubject 호출
      } //프록시가 호출한 대상 : target
        return cacheValue;      //두번째 호출일때는 null 이 아니니까 cacheValue 로 호출
    }
}
