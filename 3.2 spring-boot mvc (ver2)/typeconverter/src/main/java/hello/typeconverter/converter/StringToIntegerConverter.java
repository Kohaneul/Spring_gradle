package hello.typeconverter.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
@Slf4j
public class StringToIntegerConverter implements Converter<String,Integer> {

    //문자->숫자로 바꾸는 컨버터
    @Override
    public Integer convert(String source) {
        log.info("convert source={}",source);
        Integer integer = Integer.valueOf(source);
        return integer;
    }

}
