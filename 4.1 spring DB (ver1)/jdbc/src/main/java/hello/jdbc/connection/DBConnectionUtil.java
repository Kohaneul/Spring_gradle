package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection(){
        try {
            Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            log.info("get connection={},class={}",connection,connection.getClass());    //객체정보 , 클래스 정보 출력
            return connection;
        }
        catch(Exception e){
            throw new IllegalArgumentException(e);
        }
        }
}
