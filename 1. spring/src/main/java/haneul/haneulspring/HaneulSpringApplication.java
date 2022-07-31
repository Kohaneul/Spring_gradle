package haneul.haneulspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HaneulSpringApplication {
	//스프링 부트를쓰면 자동으로 스프링 빈으로 등록된다..=> 사실상 Component scan을 쓸 필요가 없음.
	//SpringBootApplication이 위치하는 패키지의 하위 패키지까지 다 뒤져서 bean으로 등록하기 떄문이다.
	public static void main(String[] args) {
		SpringApplication.run(HaneulSpringApplication.class, args);
	}

}