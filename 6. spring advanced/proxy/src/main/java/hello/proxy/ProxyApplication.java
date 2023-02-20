package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@Import(AppV1Config.class)	//클래스를 스프링 빈으로 등록
@Import({AppV1Config.class,AppV2Config.class})
//@Import(AppV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
//(@Configuration 이 붙었으나 스프링 빈으로 자동 등록되지 않도록 hello.proxy.app 로 컴포넌트 시작 위치 설정
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}