package com.ukj.exam.board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    String cmd;

    Connection conn = null;

    while (true) {
      System.out.printf("\n명령어 > ");
      cmd = sc.nextLine().trim();
      Rq rq = new Rq(cmd);

      // DB 연결 시작
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.err.println("예외: MySQL 드라이브 클래스가 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "guest", "bemyguest");

        doAction(sc, cmd, conn, rq);

      } catch (SQLException e) {
        System.err.println("예외: DB에 연결할 수 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;

      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();

          }

        } catch (SQLException e) {
          e.printStackTrace();

        }

      } // DB 연결 끝

    }

  }

  private void doAction(Scanner sc, String cmd, Connection conn, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/list")) { // 게시물 리스트

      PreparedStatement pstat = null;
      ResultSet rs = null;
      List<Article> articles = new ArrayList<>();

      try {
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

      } catch (SQLException e) {
        System.out.println("에러: " + e);
      }

      System.out.println("\n== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 없습니다.");
        return;
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

      PreparedStatement pstat = null;

      try {
        // 쿼리 작성 부분
        String query = "insert into article (regDate, updateDate, title, body) \n" +
            "values (now(), now(), \"" + title + "\", \"" + body + "\");";

        pstat = conn.prepareStatement(query);
        int affectedRows = pstat.executeUpdate();


      } catch (SQLException e) {
        System.out.println("에러: " + e);

      }

    }
    else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.print("새 제목: ");
      String title = sc.nextLine();
      System.out.print("새 내용: ");
      String body = sc.nextLine();

      PreparedStatement pstat = null;

      try {
        // 쿼리 작성 부분
        String query = "update article ";
        query += "set updateDate = now() ";
        query += ", title = \"" + title + "\"";
        query += ", body = \"" + body + "\"";
        query += "where id = " + id + ";";

        pstat = conn.prepareStatement(query);
        pstat.executeUpdate();

      } catch (SQLException e) {
        System.out.println("에러: " + e);

      }

    }
    else if (cmd.equals("exit")) {
      System.out.println("시스템 종료");
      System.exit(0); // 프로그램 즉시 종료

    }
    else {
      System.out.println("잘못된 명령어입니다.");
    }

  }

}
