package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;


public class ConversionServiceTest {
    @Test
    void conversionService(){
        //컨버터 등록
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        //사용

        //1) 문자 10을 입력했을때 숫자 10으로 반환받고 싶을떄
        assertThat(conversionService.convert("10",Integer.class)).isEqualTo(10);

        //2) 숫자 10을 입력했을때 문자 10으로 반환받고 싶을떄
        assertThat(conversionService.convert(10,String.class)).isEqualTo("10");

        //3) 문자를 ipPort 객체로 반환받고 싶을떄
        IpPort result = conversionService.convert("127.1.8.8:8080", IpPort.class);
        assertThat(result).isEqualTo(new IpPort("127.1.8.8",8080));

        //4) ipPort 객체를 문자로 반환받고 싶을떄
        String str = conversionService.convert(new IpPort("127.1.8.8", 8080), String.class);
        assertThat(str).isEqualTo("127.1.8.8:8080");
    }
}
