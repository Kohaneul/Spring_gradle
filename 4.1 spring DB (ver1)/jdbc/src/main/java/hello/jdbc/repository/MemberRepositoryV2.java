package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.DBConnectionUtil.getConnection;

/**
 * JDBC - ConnectionParam 사용
 *
 * */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV2 {

    private final DataSource dataSource;    //추상화된 인터페이스에만 의존 => 구현 코드가 바뀌어도 고치지 않아도 된다.

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?,?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            int cnt = pstmt.executeUpdate();//준비된 쿼리가 실제 db에 실행이 된다.
            //int 값 반환 : 실행하면서 변화된 줄이 몇줄인지
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {
            close(con, pstmt, null);
        }
    }



    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection={}, class={}",con,con.getClass());
        return con; //dataSource로 얻은 커넥션 반환
    }


    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            rs = pstmt.executeQuery();
            if(rs.next()){  //pk를 호출하기 때문에 0 또는 1개 ==> while문이 아닌 if문으로 조회
                Member member = new Member();
                member.setMemberId(rs.getString("member_Id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            else{
                throw new NoSuchElementException("member not found memberId="+memberId);
            }
        }
        catch(SQLException e){
            log.info("db error",e);
            throw e;
        }
        finally{
            close(con,pstmt,rs);
        }

    }



    public Member findById(Connection con, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {  //pk를 호출하기 때문에 0 또는 1개 ==> while문이 아닌 if문으로 조회
                Member member = new Member();
                member.setMemberId(rs.getString("member_Id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (SQLException e) {
            log.info("db error", e);
            throw e;
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
//            JdbcUtils.closeConnection(con);        //***서비스계층에서 커넥션이 종료해야함.

        }
    }



    public void update(Connection con,String memberId,int money) throws SQLException{
        PreparedStatement pstmt = null;
        String sql = "update member set money=? where member_Id=?";
        try{
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}",resultSize);
        }
        catch(SQLException e){
            log.info("db error",e);
            throw e;
        }
        finally {
            JdbcUtils.closeStatement(pstmt);

        }
    }



    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_Id=?";
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = getConnection();
            pstmt= con.prepareStatement(sql);
            pstmt.setString(1,memberId);
            int cnt = pstmt.executeUpdate();
            log.info("cnt={}",cnt);
        }
        catch(SQLException e){
            log.info("error",e);
            throw e;
        }
        finally{
            close(con,pstmt,null);
        }

    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }


}
