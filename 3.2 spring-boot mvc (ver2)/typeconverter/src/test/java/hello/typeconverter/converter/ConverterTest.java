package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.assertj.core.api.AbstractIterableSizeAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {
    @Test
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer convert = converter.convert("10");
        assertThat(convert).isEqualTo(10);
    }

    @Test
    void integerToString(){
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String convert = converter.convert(10);
        assertThat(convert).isEqualTo("10");
    }

    @Test
    void stringToIpPort(){
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        String result = converter.convert(ipPort);
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void ipPortToString(){
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        IpPort ipPort = converter.convert(source);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1",8080));
        //EqualsAndHashCode = > 주소값이 달라도 안에 있는 데이터가 같다면 true
    }

}
