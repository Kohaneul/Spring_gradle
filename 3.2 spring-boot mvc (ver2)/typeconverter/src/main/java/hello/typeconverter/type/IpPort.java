package hello.typeconverter.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

//127.0.0.1:8080
@Getter
@EqualsAndHashCode          //EqualsAndHashCode = > 참조값이 달라도 안에 있는 데이터가 같다면 true
@AllArgsConstructor
public class IpPort {

    private String ip;
    private int port;
}
