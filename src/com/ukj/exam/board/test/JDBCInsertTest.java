package com.ukj.exam.board.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
  public static void main(String[] args) {

    Connection conn = null;
    PreparedStatement pstat = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      conn = DriverManager.getConnection(url, "guest", "bemyguest");

      // 쿼리 작성 부분

      String query = "insert into article (regDate, updateDate, title, body) \n" +
          "values (now(), now(), concat('title', rand()), concat('body', rand()));";

      pstat = conn.prepareStatement(query);
      int affectedRows = pstat.executeUpdate();

      System.out.println("affectedRows: " + affectedRows);

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");

    } catch (SQLException e) {
      System.out.println("에러: " + e);


    } finally { // Connection, PreparedStatement 객체는 garbage collector가 수거하지 않아, 직접 메모리해제를 해줘야 한다.
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();

        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        if (pstat != null && !pstat.isClosed()) {
          pstat.close();

        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

}
