package com.ukj.exam.board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    int articleLastId = 0;
    String cmd;

    while (true) {
      System.out.printf("\n명령어 > ");
      cmd = sc.nextLine().trim();
      Rq rq = new Rq(cmd);

      if (rq.getUrlPath().equals("/usr/article/list")) { // 게시물 리스트

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

        System.out.println("\n== 게시물 리스트 ==");

        if (articles.isEmpty()) {
          System.out.println("게시물이 없습니다.");
          continue;
        }

        System.out.println("번호 / 제목");

        for (Article article : articles) {
          System.out.printf("% 2d / %s\n", article.id, article.title);
        }

      }
      else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 생성
        System.out.println("\n== 게시물 생성 ==");
        System.out.printf("제목: ");
        String title = sc.nextLine();
        System.out.printf("내용: ");
        String body = sc.nextLine();

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "guest", "bemyguest");

          // 쿼리 작성 부분

          String query = "insert into article (regDate, updateDate, title, body) \n" +
              "values (now(), now(), \"" + title + "\", \"" + body + "\");";

          pstat = conn.prepareStatement(query);
          int affectedRows = pstat.executeUpdate();


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
      else if (rq.getUrlPath().equals("/usr/article/modify")) {
        int id = rq.getIntParam("id", 0);

        if (id == 0) {
          System.out.println("id를 올바르게 입력해주세요.");
          continue;
        }

        System.out.print("새 제목: ");
        String title = sc.nextLine();
        System.out.print("새 내용: ");
        String body = sc.nextLine();


        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "guest", "bemyguest");

          // 쿼리 작성 부분

          String query = "update article ";
          query += "set updateDate = now() ";
          query += ", title = \"" + title + "\"";
          query += ", body = \"" + body + "\"";
          query += "where id = " + id + ";";

          pstat = conn.prepareStatement(query);
          pstat.executeUpdate();

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");

        } catch (SQLException e) {
          System.out.println("에러: " + e);


        } finally { // Connection, PreparedStatement 객체는 garbage collector가 수거하지 않아, 직접 메모리해제를 해줘야 한다.
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
      else if (cmd.equals("exit")) {
        System.out.println("시스템 종료");
        break;

      }
      else {
        System.out.println("잘못된 명령어입니다.");
      }


    }

  }

}
