package com.ukj.exam.board.test;

import com.ukj.exam.board.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {

    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;
    List<Article> articles = new ArrayList<>();

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      conn = DriverManager.getConnection(url, "guest", "bemyguest");

      // 쿼리 작성 부분

      String query = "select * ";
      query += "from article ";
      query += "order by id desc ";

      pstat = conn.prepareStatement(query);
      rs = pstat.executeQuery();

      while (rs.next()) {
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String title = rs.getString("title");
        String body = rs.getString("body");

        Article article = new Article(id, regDate, updateDate, title, body);
        articles.add(article);
      }

      System.out.println("결과: " + articles);

    } catch (ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");

    } catch (SQLException e) {
      System.out.println("에러: " + e);


    } finally { // Connection, PreparedStatement 객체는 garbage collector가 수거하지 않아, 직접 메모리해제를 해줘야 한다.
      try {
        if (rs != null && !pstat.isClosed()) {
          rs.close();

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
