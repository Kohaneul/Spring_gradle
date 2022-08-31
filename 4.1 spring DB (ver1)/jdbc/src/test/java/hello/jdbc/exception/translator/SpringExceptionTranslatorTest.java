package hello.jdbc.exception.translator;

import hello.jdbc.connection.ConnectionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLErrorCodes;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
@Slf4j
public class SpringExceptionTranslatorTest {

    DataSource dataSource;

    @BeforeEach
    void init(){
        dataSource = new DriverManagerDataSource(URL,USERNAME,PASSWORD);
    }

    @Test
    void sqlExceptionErrorCode(){
        String sql="select bad grammer";    //문법이 잘못된 sql
        try{
            Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
        }
        catch(SQLException e){
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);   //참고 : 42122 SQL 문법 오류
            int errorCode = e.getErrorCode();
            log.info("errorCode={}",errorCode);
            log.info("error",e);
        }

    }

    @Test
    void exceptionTranslator(){
        String sql = "select bad grammar";
        try{
            Connection con = dataSource.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeQuery();
        }
        catch(SQLException e){
            Assertions.assertThat(e.getErrorCode()).isEqualTo(42122);

            //스프링 예외계층 변환
            SQLErrorCodeSQLExceptionTranslator exTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);

            //BadSqlGrammarException
            DataAccessException resultEx = exTranslator.translate("select", sql, e);//sqlEXception을 분석해서 변환해줌
            log.info("resultEx={}",resultEx);
            Assertions.assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);
        }
    }

}
