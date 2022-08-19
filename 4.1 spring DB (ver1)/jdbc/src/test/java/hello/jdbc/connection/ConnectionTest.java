package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {
    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        log.info("con1={}, class={}",con1,con1.getClass());
        log.info("con2={}, class={}",con2,con2.getClass());
    }

    //driverManager 새로운 커넥션을 생성해야함.
    //dataSource :  생성하는 시점에는= dataSource를 넣어줌, 사용할때는 셋팅한 dataSource를 사용

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(30);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);

    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
       DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);//DriverManagerDataSource는 DataSource를 구현
        useDataSource(dataSource);


    }
    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("con1={}, class={}",con1,con1.getClass());
        log.info("con2={}, class={}",con2,con2.getClass());

    }
}
