package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;
@Slf4j
public class UnCheckedAppTest {


    @Test
    void unchecked(){
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(()->controller.request()).isInstanceOf(RuntimeSQLException.class);

    }

    @Test
    void printEx(){
        Controller controller = new Controller();
        try{
            controller.request();
        }
        catch(Exception e){
            log.info("ex",e);
        }
    }

    static class Controller{
        Service service = new Service();
        void request(){
            service.logic();
        }

    }
    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic(){
            repository.call();
            networkClient.call();
        }
    }





    static class NetworkClient{
        public void call(){
            throw new RuntimeConnectException("연결 실패");
        }
    }


    static class Repository{
        public void call(){
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException(e);
                //예외 변환 : SQLException이 발생하면 RuntimeSQLException으로 다룬다.
            }
        }

        private void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }


    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message){
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException(Throwable cause) {
            super(cause);  //★★★★★★ 예외를 전환할때 꼭 기존 예외를 포함해주자
        }
    }
}
