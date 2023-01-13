package com.ukj.exam.board;

import com.ukj.exam.board.util.DBUtil;
import com.ukj.exam.board.util.SecSql;

import java.sql.*;
import java.util.*;

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

        // DB 연결 중

        // 여러가지

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

      }

    }

  }

  private void doAction(Scanner sc, String cmd, Connection conn, Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/list")) { // 게시물 리스트

      List<Article> articles = new ArrayList<>();

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List< Map<String, Object> > articleListMap = DBUtil.selectRows(conn, sql);

      for (Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
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

    else if (rq.getUrlPath().equals("/usr/article/detail")) { // 게시물 상세 보기
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

      if (articleMap.isEmpty()) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.", id);
        return;
      }

      Article article = new Article(articleMap);

      System.out.printf("\n== %d번 게시물 ==\n", id);
      System.out.printf("번호: %d\n", article.id);
      System.out.printf("작성 날짜: %s\n", article.regDate);
      System.out.printf("수정 날짜: %s\n", article.updateDate);
      System.out.printf("제목: %s\n", article.title);
      System.out.printf("내용: %s\n", article.body);

    }

    else if (rq.getUrlPath().equals("/usr/article/write")) {  // 게시물 생성
      System.out.println("\n== 게시물 생성 ==");
      System.out.printf("제목: ");
      String title = sc.nextLine();
      System.out.printf("내용: ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("(regDate, updateDate, title, body) VALUES");
      sql.append("(NOW(), NOW(), ?, ?)", title, body);

      int id = DBUtil.insert(conn, sql);

      System.out.printf("%d번 게시물이 생성되었습니다.\n", id);

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

      SecSql sql = new SecSql();
      sql.append("UPDATE article SET");
      sql.append("updateDate = NOW(),");
      sql.append("title = ?,", title);
      sql.append("body = ?", body);
      sql.append("WHERE id = ?", id);

      DBUtil.update(conn, sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);


    }

    else if (rq.getUrlPath().equals("/usr/article/delete")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt FROM article");
      sql.append("WHERE id = ?", id);

      int articlesCount = DBUtil.selectRowIntValue(conn, sql);

      if(articlesCount != 1) {
        System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
        return;
      }

      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      DBUtil.delete(conn, sql);

      System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

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
