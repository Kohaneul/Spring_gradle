package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    MessageSource ms;    //SpringBootTest를 하게되면 자동으로 MessageSource 등록해줌

    @Test
    void helloMessage(){

        String result = ms.getMessage("hello", null, null);//1. 코드, 2. argument, 3. locale(한국 or 영어)정보
        //locale 정보가 없을시 default가 선택된다.
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode(){
    //    ms.getMessage("no_code",null,null);
       assertThatThrownBy(()->ms.getMessage("no code",null,null)).isInstanceOf(NoSuchMessageException.class);
        //"no code" 라는 메세지가없으면 NoSuchMessageException(메세지를 찾을 수 없다) 이 실행된다.
    }

    @Test
    void notFoundMessageCode2(){
        String result = ms.getMessage("no_code", null, "기본메시지", null);
        assertThat(result).isEqualTo("기본메시지");
    }


    @Test
    void argumentMessage(){
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");  //안녕 {0} => 안녕 + Spring
    }

    @Test
    void defaultLang(){
        assertThat(ms.getMessage("hello",null,null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello",null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang(){
        assertThat(ms.getMessage("hello",null,Locale.ENGLISH)).isEqualTo("hello");
    }

}
