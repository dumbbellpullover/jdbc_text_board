package com.ukj.exam.board.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionTest {
  public static void main(String[] args) {


    Connection conn = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      conn = DriverManager.getConnection(url, "guest", "bemyguest");

      // 쿼리 작성 부분

      System.out.println("연결 성공");

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");

    } catch (SQLException e) {
      System.out.println("에러: " + e);

    } finally { // Connection 객체는 garbage collector가 수거하지 않아, 직접 메모리해제를 해줘야 한다.
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();

        }

      } catch (SQLException e) {
        e.printStackTrace();
      }

    }

  }

}