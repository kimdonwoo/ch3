package com.fastcampus.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired DataSource ds;

    @Test
    public void insertUserTest() throws Exception{
        User user = new User("aadasdca","1234","abc","aaa@aaa.com",new java.util.Date(),"fb",new java.util.Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    private void deleteAll() throws Exception{

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻음

        String sql = "delete from user_info";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate(); // insert,delete,update

    }

    @Test
    public void selectUserTest() throws Exception{
        deleteAll();

        User user = new User("aadasdca","1234","abc","aaa@aaa.com",new java.util.Date(),"fb",new java.util.Date());
        int rowCnt = insertUser(user);

        User user2 = selectUser("aadasdca");

        assertTrue(user2.getId().equals("aadasdca"));

    }

    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();

        int rowCnt = deleteUser("aadasdca");

        assertTrue(rowCnt == 0);

        User user = new User("aadasdca","1234","abc","aaa@aaa.com",new java.util.Date(),"fb",new java.util.Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt == 1);

        rowCnt = deleteUser(user.getId());

        assertTrue(rowCnt == 1);

        assertTrue(selectUser(user.getId()) == null);
    }


    // 매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메서드
//    public int updateUser(User user) throws Exception{
//
//
//    }

    public int deleteUser(String id) throws Exception{
        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻음

        String sql = " delete from user_info where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        int rowCnt = pstmt.executeUpdate(); // insert,delete,update

        return rowCnt;
    }



    public User selectUser(String id) throws Exception{

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻음

        String sql = "select * from user_info where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,id);
        ResultSet rs = pstmt.executeQuery(); // insert,delete,update

        if(rs.next()){
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }

        return null;
    }

    @Test
    public void transactionTest() throws Exception{
        Connection conn = null;

        try {
            deleteAll();
            conn = ds.getConnection();
            conn.setAutoCommit(false); // 이걸 안하면 autocommit인 상태라서 제대로 처리할수 x

            String sql = "insert into user_info values (?,?,?,?,?,?,now())";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"asdf");
            pstmt.setString(2,"1234");
            pstmt.setString(3,"abc");
            pstmt.setString(4,"aaa@aaa");
            pstmt.setDate(5,new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setString(6,"fb");

            int rowCnt = pstmt.executeUpdate();

            pstmt.setString(1, "asdf2");
            rowCnt = pstmt.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        }finally {

        }

    }

    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws  Exception{

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻음

        String sql = "insert into user_info values (?,?,?,?,?,?,now())";

        // PreparedStatement를 통해 values 안에 ? 채우고 밑에서 setString (",' 복잡하지 않게)
        // SQL Injection 공격방지, 성능향상
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getId());
        pstmt.setString(2,user.getPwd());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getEmail());
        pstmt.setDate(5,new java.sql.Date(user.getBirth().getTime()));
        pstmt.setString(6,user.getSns());

        int rowCnt = pstmt.executeUpdate(); // insert,delete,update

        return rowCnt;
    }

    @Test
    public void main() throws Exception{

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null); // 괄호 안의 조건식이 true면, 테스트 성공 아니면 실패

    }
}