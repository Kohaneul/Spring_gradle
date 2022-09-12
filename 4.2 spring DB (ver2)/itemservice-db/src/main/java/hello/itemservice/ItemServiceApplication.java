package hello.itemservice;

import hello.itemservice.config.*;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV3Config.class)

@Slf4j
@Import(V2Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")	//특정 프로필이 사용되는 경우에만 testDataInit이라는 스프링 빈을 등록.(application.properties 에서 spring.profiles.active=local 설정함)
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

//	해당 코드는 test시 스프링 부트가 자동으로 생성해줌
//	@Bean
//	@Profile("test")	//프로필이 test인 경우에 dataSource를 스프링 빈으로 등록
//	public DataSource dataSource(){
//		log.info("메모리 데이터베이스 초기화");
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");	//h2 Database 지정
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//		//★★★데이터소스를 만들때 jdbc:h2:mem:db 로 적으면 임베디드모드(메모리모드)로 동작하는 h2 데이터베이스를 사용할 수 있다.
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;
//	}


}
